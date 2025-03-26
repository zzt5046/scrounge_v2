package zjt.projects.db.models.account

data class AccountLoginResponse (
    val accountId: String?,
    val status: AccountLoginStatus
)