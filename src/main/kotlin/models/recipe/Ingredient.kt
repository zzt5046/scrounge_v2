package zjt.projects.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    val measurement: Measurement,
    val name: String,
)