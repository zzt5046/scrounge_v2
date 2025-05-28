package zjt.projects.models.account

import kotlinx.serialization.Serializable
import zjt.projects.models.account.settings.AccountSetting

@Serializable
data class AccountUpdateRequest (
    val accountId: String,
    val userName: String?, //we don't support changing this yet, but including it here anyway
    val emailAddress: String?,
    val securityQuestionId: String?,
    val securityQuestionAnswer: String?,
    val settings: Map<AccountSetting, String>?,
    val favoriteRecipes: Set<String>?,
)