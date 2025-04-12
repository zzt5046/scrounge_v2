package zjt.projects.models.account

data class AccountLoginResponse (
    val accountId: String?,
    val status: AccountLoginStatus,
    val settings: Map<AccountSetting, String>?,
)

enum class AccountLoginStatus {
    SUCCESS,
    FAILURE,
    UNKNOWN
}