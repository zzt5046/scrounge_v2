package zjt.projects.db.models.account

data class AccountLoginRequest (
    val userName: String,
    val credentials: String
)