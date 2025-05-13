package zjt.projects.models.account

import zjt.projects.models.account.settings.AccountSetting

data class AccountLoginResponse (
    val accountId: String?,
    val userName: String?,
    val status: AccountLoginStatus,
    val settings: Map<AccountSetting, String>?,
)

enum class AccountLoginStatus {
    SUCCESS,
    FAILURE,
    UNKNOWN
}