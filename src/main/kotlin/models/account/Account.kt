package zjt.projects.models.account

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val userName: String,
    val credentials: String,
    val emailAddress: String?,
    val securityQuestionId: String,
    val securityQuestionAnswer: String,
    val settings: Map<AccountSetting, String>,
    val favoriteRecipes: List<String> = listOf()
){
    companion object {
        fun Account?.toAccountResponse(): AccountResponse? {
            return if(this == null){
                null
            }else{
                AccountResponse(
                    userName = userName,
                    emailAddress = emailAddress,
                    settings = settings,
                    favoriteRecipes = favoriteRecipes
                )
            }
        }
    }
}