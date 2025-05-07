package zjt.projects.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.util.reflect.*
import kotlinx.serialization.SerializationException

fun Application.configureStatusPages(){
    install(StatusPages) {
        exception<SerializationException> { call, cause ->
            call.application.environment.log.error("❌ Serialization failed: ${cause.message}", cause)
            call.respond("${HttpStatusCode.BadRequest} - Invalid JSON: ${cause.message}", TypeInfo(String::class))
        }

        exception<BadRequestException> { call, cause ->
            call.application.environment.log.error("Bad Request: ${cause.message}", cause)
            call.respond("${HttpStatusCode.BadRequest} - Bad Request: ${cause.message}", TypeInfo(String::class))
        }

        exception<Throwable> { call, cause ->
            call.application.environment.log.error("❗ Unexpected error: ${cause.message}", cause)
            call.respond("${HttpStatusCode.InternalServerError} - Server error: ${cause.message}", TypeInfo(String::class))
        }
    }
}