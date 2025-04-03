package zjt.projects.models.recipe

import kotlinx.serialization.Serializable
import zjt.projects.models.account.MeasurementSystem

@Serializable
data class Measurement (
    val type: MeasurementType,
    val quantity: Double,
    val measurementSystem: MeasurementSystem,
    val measurementType: MeasurementType,
    val measurementUnit: MeasurementUnit
)

enum class MeasurementType {
    VOLUME,
    MASS
}

sealed interface MeasurementUnit {
    companion object {
        fun fromString(value: String): MeasurementUnit? {
            return MetricUnit.entries.find { it.name.equals(value, ignoreCase = true) }
                ?: ImperialUnit.entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

enum class ImperialUnit(label: String): MeasurementUnit {
    FLUID_OZ("fl oz"),
    GILL("gill(s)"),//5 fl oz
    CUP("c"),
    PINT("pt"),
    QUART("qt"),
    GALLON("gal"),
    OUNCE("oz"),
    POUND("lb"),
}
enum class MetricUnit(label: String): MeasurementUnit {
    MILLILITER("mL"),
    LITER("L"),
    MILLIGRAM("mg"),
    GRAM("g"),
    KILOGRAM("kg")
}