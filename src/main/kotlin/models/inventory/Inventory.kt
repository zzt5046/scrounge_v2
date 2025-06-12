package zjt.projects.models.inventory

import kotlinx.serialization.Serializable

@Serializable
data class Inventory (
    val accountId: String,
    val ingredients: List<String>
)