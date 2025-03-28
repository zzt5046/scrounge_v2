package zjt.projects.models.account

data class AccountLoginResponse (
    val accountId: String?,
    val status: AccountLoginStatus
)

enum class AccountLoginStatus {
    SUCCESS,
    FAILURE,
    UNKNOWN
}