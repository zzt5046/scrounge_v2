package zjt.projects.db.modules

import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import zjt.projects.db.operations.AccountService
import zjt.projects.models.account.*
import zjt.projects.models.error.ScroungeError
import zjt.projects.services.SessionService

fun Application.accountsModule(db: MongoDatabase){
    val accountService = AccountService(db)
    val sessionService = SessionService()

    routing {
        //Auth account (login)
        post("/accounts/login") {
            val request = call.receive<AccountLoginRequest>()

            val loginResponse = accountService.authenticate(request)
            when(loginResponse.status){
                AccountLoginStatus.SUCCESS -> {
                    val accountId = loginResponse.accountId
                    var accountResponse: AccountResponse? = null
                    if(!accountId.isNullOrEmpty()){
                        accountResponse = accountService.findAccount(accountId)
                        call.response.cookies.append(sessionService.getSessionCookie(loginResponse.accountId))
                    }
                    call.respond(HttpStatusCode.OK, accountResponse, TypeInfo(AccountResponse::class))
                }
                AccountLoginStatus.FAILURE -> {
                    call.respond(HttpStatusCode.Unauthorized, 401)
                }
                AccountLoginStatus.UNKNOWN -> {
                    call.respond(HttpStatusCode.InternalServerError, 500)
                }
            }
        }

        // Create account
        post("/accounts") {
            val request = call.receive<AccountCreateRequest>()
            val id = accountService.create(request)
            call.respond(HttpStatusCode.Created, id)
        }

        // Get account by id
        get("/accounts/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
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

        // Get account settings
        get("/accounts/{id}/settings") {
            try{
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                call.respond(accountService.getAccountSettings(id))
            }catch(e: Exception){
                call.respond(AccountResponse(
                    userName = null,
                    settings = null,
                    emailAddress = null,
                    errors = listOf(ScroungeError(400, "No ID Found"))
                ))
            }
        }

        // Update account
        put("/accounts/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            val account = call.receive<Account>()
            accountService.update(id, account)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Delete account
        delete("/accounts/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            accountService.delete(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}