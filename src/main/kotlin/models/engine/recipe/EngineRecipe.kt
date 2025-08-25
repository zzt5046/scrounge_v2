package zjt.projects.models.engine.recipe

import kotlinx.serialization.Serializable

@Serializable
data class EngineRecipe (
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val directions: List<String>,
    val notes: String,

    //this should only be present if saving from generated recipes
    val accountId: String? = "",

    //keeping these default just in the case the frontend needs them
    val public: Boolean? = false,
    val author: String? = "Scrounge SmartFood",
    val generated: Boolean? = true,
)