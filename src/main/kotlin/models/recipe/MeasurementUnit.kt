package zjt.projects.models.recipe

import kotlinx.serialization.Serializable
import zjt.projects.models.account.settings.MeasurementSystem

@Serializable
enum class MeasurementUnit(val label: String, val system: MeasurementSystem?) {

    //IMPERIAL
    TEASPOON("tsp", MeasurementSystem.IMPERIAL),
    TABLESPOON("tbsp", MeasurementSystem.IMPERIAL),
    OUNCE("oz", MeasurementSystem.IMPERIAL),
    POUND("lb", MeasurementSystem.IMPERIAL),
    CUP("c", MeasurementSystem.IMPERIAL),
    PINT("pt", MeasurementSystem.IMPERIAL),
    QUART("qt", MeasurementSystem.IMPERIAL),
    GALLON("gal", MeasurementSystem.IMPERIAL),
    FLUID_OZ("fl oz", MeasurementSystem.IMPERIAL),

    //METRIC
    MILLILITER("mL", MeasurementSystem.METRIC),
    LITER("L", MeasurementSystem.METRIC),
    MILLIGRAM("mg", MeasurementSystem.METRIC),
    GRAM("g", MeasurementSystem.METRIC),
    KILOGRAM("kg", MeasurementSystem.METRIC),

    //for "no unit" cases, e.g. 3 onions
    ITEM("", MeasurementSystem.UNIVERSAL),
}

fun getMeasurementUnits(system: MeasurementSystem): Map<MeasurementUnit, String> =
    MeasurementUnit.entries.filter { it.system == system || it.system == MeasurementSystem.UNIVERSAL }.associateWith { it.label }
