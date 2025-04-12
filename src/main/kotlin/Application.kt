package zjt.projects

import io.ktor.server.application.*
import zjt.projects.config.*
import zjt.projects.db.connectToMongoDB
import zjt.projects.db.modules.accountsModule
import zjt.projects.db.modules.recipesModule
import zjt.projects.db.modules.securityQuestionsModule

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureMonitoring()
    configureSessions()
    configureSessionAuthentication()
    configureRouting()
    configureStatusPages()
}

fun Application.configureDatabases() {
    connectToMongoDB().also {
        securityQuestionsModule(it)
        accountsModule(it)
        recipesModule(it)
    }
}
