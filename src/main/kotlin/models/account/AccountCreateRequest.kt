package zjt.projects.models.account

data class AccountCreateRequest(
    val userName: String,
    val password:  String,
    val emailAddress: String?,
    val securityQuestionId: String,
    val securityQuestionAnswer: String,
)