package zjt.projects.models.security_question

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class SecurityQuestion(
    val question: String
)