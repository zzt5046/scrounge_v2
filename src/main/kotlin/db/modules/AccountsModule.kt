package zjt.projects.db.modules

import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import zjt.projects.db.models.account.Account
import zjt.projects.db.models.account.AccountCreateRequest
import zjt.projects.db.models.account.AccountLoginRequest
import zjt.projects.db.models.account.AccountLoginStatus
import zjt.projects.db.operations.AccountService
import zjt.projects.services.SessionService

fun Application.accountsModule(db: MongoDatabase){
    val accountService = AccountService(db)
    val sessionService = SessionService()

    routing {
        //Auth account (login)
        post("/accounts/login") {
            val request = call.receive<AccountLoginRequest>()

            val status = accountService.authenticate(request).status
            if(status == AccountLoginStatus.SUCCESS){
                val accountDoc = accountService.readDocumentByUsername(request.userName)
                call.response.cookies.append(sessionService.getSessionCookie(accountDoc))
                call.respond(HttpStatusCode.OK, 200)
            }else if(status == AccountLoginStatus.FAILURE){
                call.respond(HttpStatusCode.Unauthorized, 401)
            }else{
                call.respond(HttpStatusCode.InternalServerError, 500)
            }
        }

        // Create account
        post("/accounts") {
            val request = call.receive<AccountCreateRequest>()
            val id = accountService.create(request)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read account by id
        get("/accounts/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            accountService.read(id)?.let { account ->
                call.respond(account)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Read account by userName
        get("/accounts/{username}") {
            val userName = call.parameters["userName"] ?: throw IllegalArgumentException("No userName found")
            accountService.readByUsername(userName)?.let { account ->
                call.respond(account)
            } ?: call.respond(HttpStatusCode.NotFound)
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