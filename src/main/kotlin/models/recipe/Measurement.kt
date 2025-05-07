package zjt.projects.models.recipe

import kotlinx.serialization.Serializable
import zjt.projects.util.serializer.recipe.MeasurementUnitSerializer

@Serializable
data class Measurement (
    val quantity: Double,
    @Serializable(with = MeasurementUnitSerializer::class)
    val measurementUnit: MeasurementUnit?
)
