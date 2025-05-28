package zjt.projects.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    val accountId: String,
    val public: Boolean,
    val author: String,
    val name: String,
    val description: String?,
    val ingredients: List<Ingredient> = listOf(),
    val directions: List<String> = listOf(),
    val notes: String? = ""
)