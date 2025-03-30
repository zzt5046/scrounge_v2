package zjt.projects.config

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSessionAuthentication() {
    install(Authentication){
        session<UserSession>("session-auth"){
            challenge("/login")
            validate { session ->
                session
            }
        }


    }
}