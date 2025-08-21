package zjt.projects.models.account

import zjt.projects.models.account.settings.AccountSetting
import zjt.projects.models.recipe.RecipeResponse

data class AccountLoginResponse (
    val status: AccountLoginStatus,
    val accountId: String?,
    val account: AccountResponse?,
    val settings: Map<AccountSetting, String>?,
    val recipes: List<RecipeResponse>?,
    val inventory: List<String>?,
)

enum class AccountLoginStatus {
    SUCCESS,
    FAILURE,
    UNKNOWN
}