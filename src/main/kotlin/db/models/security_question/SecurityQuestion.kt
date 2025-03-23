package zjt.projects.db.models.security_question

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class SecurityQuestion(
    val question: String
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
        fun fromDocument(document: Document): SecurityQuestion = json.decodeFromString(document.toJson())
        fun toResponse(document: Document): SecurityQuestionResponse {
            return SecurityQuestionResponse(
                document["_id"].toString(),
                document["question"].toString()
            )
        }
    }
}