package zjt.projects.config

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable

fun Application.configureSessions(){
    install(Sessions){
        cookie<UserSession>(UserSession.COOKIE_NAME){
            cookie.path = "/"
            cookie.maxAgeInSeconds = 900
        }
    }
}

@Serializable
data class UserSession (
    val accountId: String
){
    companion object {
        const val COOKIE_NAME = "scrounge-session"
    }
}