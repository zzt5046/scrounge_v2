package zjt.projects.util.serializer.recipe

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import zjt.projects.models.recipe.MeasurementUnit


object MeasurementUnitSerializer : KSerializer<MeasurementUnit> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MeasurementUnit", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: MeasurementUnit) {
        encoder.encodeString(value.label)
    }

    override fun deserialize(decoder: Decoder): MeasurementUnit {
        val label = decoder.decodeString()
        return allUnits[label] ?:
        if(label.isEmpty()) {
             MeasurementUnit.NO_UNIT
        }else{
            throw SerializationException("Unknown MeasurementUnit label: '$label'")
        }
    }

    // Combine all units into a map for quick lookup by label
    private val allUnits: Map<String, MeasurementUnit> = buildMap {
        MeasurementUnit.entries.forEach { put(it.label, it) }
    }
}