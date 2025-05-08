package db.services

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.bson.Document
import zjt.projects.models.security_question.SecurityQuestion
import zjt.projects.models.security_question.SecurityQuestionResponse

class SecurityQuestionService(database: MongoDatabase, questions: List<String>) {
    private var collection: MongoCollection<Document>

    init {
        database.createCollection("security_questions")
        collection = database.getCollection("security_questions")
        if(collection.countDocuments().toInt() == 0){
            collection.insertMany(questions.map { SecurityQuestion(it).toDocument() })
        }
    }

    suspend fun getAll(): List<SecurityQuestionResponse> = withContext(Dispatchers.IO) {
        val questions = mutableListOf<SecurityQuestionResponse>()
        collection.find().forEach { questions.add(it.toSecurityQuestionResponse()) }
        questions
    }

    private fun Document.toSecurityQuestion(): SecurityQuestion = json.decodeFromString(this.toJson())

    private fun Document.toSecurityQuestionResponse(): SecurityQuestionResponse {
        return SecurityQuestionResponse(
            this["_id"].toString(),
            this["question"].toString()
        )
    }

    private fun SecurityQuestion.toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }
}

