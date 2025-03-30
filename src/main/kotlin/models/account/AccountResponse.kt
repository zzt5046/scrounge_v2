package zjt.projects.models.account

import zjt.projects.models.error.ScroungeError

data class AccountResponse (
    val userName: String?,
    val emailAddress: String?,
    val settings: Map<AccountSetting, String>?,
    val errors: List<ScroungeError>?
){
    companion object {
        val Unauthorized = AccountResponse(
            userName = null,
            emailAddress = null,
            settings = null,
            errors = listOf(ScroungeError.Unauthorized)
        )
        val ServerError = AccountResponse(
            userName = null,
            emailAddress = null,
            settings = null,
            errors = listOf(ScroungeError.ServerError)
        )
    }
}