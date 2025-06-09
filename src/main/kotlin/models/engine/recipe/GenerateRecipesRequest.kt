package zjt.projects.models.engine.recipe

import kotlinx.serialization.Serializable

@Serializable
data class GenerateRecipesRequest(
    val ingredients: List<String>,
    val preferences: List<String>,
    val count: Int
)