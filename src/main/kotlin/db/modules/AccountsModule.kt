package zjt.projects.db.modules

import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.reflect.*
import zjt.projects.config.UserSession
import zjt.projects.db.operations.AccountService
import zjt.projects.models.account.*
import zjt.projects.models.account.settings.getAllSettings
import zjt.projects.util.crypto.sha256

fun Application.accountsModule(db: MongoDatabase){
    val accountService = AccountService(db)

    val targetAccountPath = "/accounts/{id}"
    val noIdFound = "No ID found"

    routing {
        //Auth account (login)
        post("/accounts/login") {
            try {
                val request = call.receive<AccountLoginRequest>()

                val loginResponse = accountService.authenticate(request)
                when(loginResponse.status){
                    AccountLoginStatus.SUCCESS -> {
                        call.respond(HttpStatusCode.OK, loginResponse, TypeInfo(AccountLoginResponse::class))
                    }
                    AccountLoginStatus.FAILURE -> {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            AccountResponse.Unauthorized,
                            TypeInfo(AccountResponse::class)
                        )
                    }
                    AccountLoginStatus.UNKNOWN -> {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            AccountResponse.ServerError,
                            TypeInfo(AccountResponse::class)
                        )
                    }
                }
            }catch (e: NullPointerException){
                call.respond(HttpStatusCode.BadRequest)
            }catch (e: Exception){
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        // Create account
        post("/accounts") {
            try{
                val request = call.receive<AccountCreateRequest>()
                val id = accountService.create(request)
                call.respond(HttpStatusCode.Created, id)
            }catch (e: NullPointerException){
                call.respond(HttpStatusCode.BadRequest)
            }catch (e: Exception){
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        // Logout
        post("/accounts/logout"){
            call.sessions.clear<UserSession>()
        }

        // Get account by id
        get(targetAccountPath) {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
                accountService.findAccount(id)?.let { account ->
                    call.respond(account)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        // Get account by userName
        get("/accounts/user/{userName}") {
            try {
                val userName = call.parameters["userName"] ?: throw IllegalArgumentException("No userName found")
                accountService.findAccountByUsername(userName)?.let { account ->
                    call.respond(account)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        //get all possible settings
        get("/accounts/settings") {
            call.respond(getAllSettings())
        }

        // Get account settings
        get("$targetAccountPath/settings") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
                call.respond(accountService.getAccountSettings(id))
            }catch(e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }catch(e: Exception){
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        // Update account
        put(targetAccountPath) {
            try{
                val request = call.receive<AccountUpdateRequest>()
                val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)

                val account = accountService.findFullAccount(accountId = id)
                checkNotNull(account)

                val securityQuestionAnswer = if (!request.securityQuestionAnswer.isNullOrEmpty()){
                    (account.salt + request.securityQuestionAnswer).sha256()
                }else{
                    account.securityQuestionAnswer
                }

                val updatedAccount = account.copy(
                    userName = request.userName ?: account.userName,
                    emailAddress = request.emailAddress ?: account.emailAddress,
                    securityQuestionId = request.securityQuestionId ?: account.securityQuestionId,
                    securityQuestionAnswer = securityQuestionAnswer,
                    settings = request.settings ?: account.settings,
                    favoriteRecipes = request.favoriteRecipes ?: account.favoriteRecipes
                )

                accountService.update(id, updatedAccount)?.let {
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: NullPointerException){
                call.respond(HttpStatusCode.BadRequest)
            }catch (e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }catch (e: BadRequestException){
                call.respond(HttpStatusCode.BadRequest)
            }catch (e: IllegalStateException){
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Delete account
        delete(targetAccountPath) {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
                accountService.delete(id)?.let {
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}