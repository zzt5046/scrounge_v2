package zjt.projects.db.modules

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoDatabase
import db.services.SecurityQuestionService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.securityQuestionsModule(db: MongoDatabase){
    val fileContent = this::class.java.classLoader.getResource("security-questions.json")?.readText()
    val questions: List<String> = Gson().fromJson(fileContent, object : TypeToken<List<String>>() {}.type)
    val securityQuestionService = SecurityQuestionService(db, questions)

    routing {
        // Get stored security questions
        get("/security-questions") {
            securityQuestionService.getAll().let { questions -> call.respond(questions) }
        }
    }
}