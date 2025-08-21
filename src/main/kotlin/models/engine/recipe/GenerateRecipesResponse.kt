package zjt.projects.models.engine.recipe

import kotlinx.serialization.Serializable

@Serializable
data class GenerateRecipesResponse(
    val recipes: List<EngineRecipe>,
    val error: String?,
)