package zjt.projects

import io.ktor.server.application.*
import zjt.projects.config.*
import zjt.projects.db.connectToMongoDB
import zjt.projects.db.modules.accountsModule
import zjt.projects.db.modules.securityQuestionsModule

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}

fun Application.configureDatabases() {
    connectToMongoDB().also {
        securityQuestionsModule(it)
        accountsModule(it)
    }
}
