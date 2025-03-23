package zjt.projects.db.models.account

data class AccountCreateRequest(
    val userName: String,
    val credentials: String,
    val emailAddress: String?,
    val securityQuestionId: String,
    val securityQuestionAnswer: String,
)