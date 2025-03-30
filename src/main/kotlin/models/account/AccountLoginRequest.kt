package zjt.projects.models.account

data class AccountLoginRequest (
    val userName: String,
    val credentials: String
)