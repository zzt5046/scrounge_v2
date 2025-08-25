package zjt.projects.models.account

import kotlinx.serialization.Serializable
import zjt.projects.db.services.InventoryService
import zjt.projects.models.account.settings.AccountSetting
import zjt.projects.models.engine.recipe.EngineRecipe

@Serializable
data class Account(
    val userName: String,
    val password: String,
    val salt: String,
    val emailAddress: String?,
    val securityQuestionId: String,
    val securityQuestionAnswer: String,
    val settings: Map<AccountSetting, String>,
    val favoriteRecipes: Set<String>,
    val generatedRecipes: MutableMap<String, EngineRecipe>
){
    companion object {
        fun Account?.toAccountResponse(): AccountResponse? {
            return if(this == null){
                null
            }else{
                AccountResponse(
                    userName = userName,
                    emailAddress = emailAddress,
                    securityQuestionId = securityQuestionId,
                    settings = settings,
                    favoriteRecipes = favoriteRecipes,
                    generatedRecipes = generatedRecipes
                )
            }
        }
    }
}