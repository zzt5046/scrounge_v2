package zjt.projects.models.account

import zjt.projects.models.account.settings.AccountSetting

data class AccountResponse (
    val userName: String?,
    val emailAddress: String?,
    val securityQuestionId: String?,
    val settings: Map<AccountSetting, String>?,
    val favoriteRecipes: Set<String> = mutableSetOf()
){
    companion object {
        val Unauthorized = AccountResponse(
            userName = null,
            emailAddress = null,
            securityQuestionId = null,
            settings = null,
        )
        val ServerError = AccountResponse(
            userName = null,
            emailAddress = null,
            securityQuestionId = null,
            settings = null,
        )
    }
}