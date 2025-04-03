package zjt.projects.models.recipe

import kotlinx.serialization.Serializable
import zjt.projects.util.serializer.recipe.RecipeSerializer

@Serializable(with = RecipeSerializer::class)
data class Recipe (
    val originAccountId: String,
    val public: Boolean,
    val name: String,
    val description: String,
    val ingredients: List<Pair<Measurement, String>> = listOf(),
    val directions: List<String> = listOf()
)