package zjt.projects.models.account

import zjt.projects.models.account.settings.AccountSetting

data class AccountUpdateRequest (
    val accountId: String,
    val userName: String?, //we don't support changing this yet, but including it here anyway
    val emailAddress: String?,
    val securityQuestionId: String?,
    val securityQuestionAnswer: String?,
    val settings: Map<AccountSetting, String>?,
    val favoriteRecipes: List<String>?,
)