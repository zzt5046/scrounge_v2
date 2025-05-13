package zjt.projects.models.account

import zjt.projects.models.account.settings.AccountSetting
import zjt.projects.models.error.ScroungeError

data class AccountResponse (
    val userName: String?,
    val emailAddress: String?,
    val securityQuestionId: String?,
    val settings: Map<AccountSetting, String>?,
    val favoriteRecipes: List<String>?
){
    companion object {
        val Unauthorized = AccountResponse(
            userName = null,
            emailAddress = null,
            securityQuestionId = null,
            settings = null,
            favoriteRecipes = null
        )
        val ServerError = AccountResponse(
            userName = null,
            emailAddress = null,
            securityQuestionId = null,
            settings = null,
            favoriteRecipes = null
        )
    }
}