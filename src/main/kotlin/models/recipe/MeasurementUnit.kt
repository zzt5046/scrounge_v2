package zjt.projects.models.recipe

import kotlinx.serialization.Serializable
import zjt.projects.models.account.MeasurementSystem

@Serializable
enum class MeasurementUnit(val label: String, val system: MeasurementSystem?) {

    //IMPERIAL
    FLUID_OZ("fl oz", MeasurementSystem.IMPERIAL),
    GILL("gill(s)", MeasurementSystem.IMPERIAL),//5 fl oz
    CUP("c", MeasurementSystem.IMPERIAL),
    PINT("pt", MeasurementSystem.IMPERIAL),
    QUART("qt", MeasurementSystem.IMPERIAL),
    GALLON("gal", MeasurementSystem.IMPERIAL),
    OUNCE("oz", MeasurementSystem.IMPERIAL),
    POUND("lb", MeasurementSystem.IMPERIAL),

    //METRIC
    MILLILITER("mL", MeasurementSystem.METRIC),
    LITER("L", MeasurementSystem.METRIC),
    MILLIGRAM("mg", MeasurementSystem.METRIC),
    GRAM("g", MeasurementSystem.METRIC),
    KILOGRAM("kg", MeasurementSystem.METRIC),

    //for "no unit" cases, e.g. 3 onions
    NO_UNIT(" ", null),
}

fun getMeasurementUnits(system: MeasurementSystem): Map<MeasurementUnit, String> =
    MeasurementUnit.entries.filter { it.system == system }.associateWith { it.label }
