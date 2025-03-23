package zjt.projects.db.schema

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.db.models.security_question.SecurityQuestion
import zjt.projects.db.models.security_question.SecurityQuestionResponse

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
        collection.find().forEach { questions.add(SecurityQuestion.toResponse(it)) }
        questions
    }

    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }
}

