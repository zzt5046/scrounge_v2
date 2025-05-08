package zjt.projects.db.modules

import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.reflect.*
import zjt.projects.config.UserSession
import zjt.projects.db.operations.AccountService
import zjt.projects.models.account.*
import zjt.projects.models.account.settings.getAllSettings
import zjt.projects.models.error.ScroungeError

fun Application.accountsModule(db: MongoDatabase){
    val accountService = AccountService(db)

    val targetAccountPath = "/accounts/{id}"
    val noIdFound = "No ID found"

    routing {
        //Auth account (login)
        post("/accounts/login") {
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
        }

        post("/accounts/login/cookie") {
            //if there is already a session cookie, let em in
            if(call.sessions.get<UserSession>() != null){
                call.respond(HttpStatusCode.OK, "cookie-auth")
            }else{
                call.respond(HttpStatusCode.Unauthorized, "No Cookie")
            }
        }

        post("/accounts/logout"){
            call.sessions.clear<UserSession>()
        }

        // Create account
        post("/accounts") {
            val request = call.receive<AccountCreateRequest>()
            val id = accountService.create(request)
            call.respond(HttpStatusCode.Created, id)
        }

        // Get account by id
        get(targetAccountPath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            accountService.findAccount(id)?.let { account ->
                call.respond(account)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Get account by userName
        get("/accounts/{username}") {
            val userName = call.parameters["userName"] ?: throw IllegalArgumentException("No userName found")
            accountService.findAccountByUsername(userName)?.let { account ->
                call.respond(account)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        //get all possible settings
        get("/accounts/settings") {
            call.respond(getAllSettings())
        }

        // Get account settings
        get("$targetAccountPath/settings") {
            try{
                val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
                call.respond(accountService.getAccountSettings(id))
            }catch(e: Exception){
                call.respond(AccountResponse(
                    userName = null,
                    settings = null,
                    emailAddress = null,
                    errors = listOf(ScroungeError(400, noIdFound)),
                    favoriteRecipes = null,
                ))
            }
        }

        // Update account
        put(targetAccountPath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            val account = call.receive<Account>()
            accountService.update(id, account)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Delete account
        delete(targetAccountPath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            accountService.delete(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}