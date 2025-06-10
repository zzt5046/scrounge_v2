package zjt.projects.models.engine.recipe

import kotlinx.serialization.Serializable

@Serializable
data class EngineRecipe (
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val directions: List<String>,
    val notes: String
)