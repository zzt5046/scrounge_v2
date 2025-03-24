package zjt.projects.db.models.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class Account(
    val userName: String,
    val credentials: String,
    val emailAddress: String?,
    val securityQuestionId: String,
    val securityQuestionAnswer: String,
    val preferences: Map<AccountPreference, String>,
)