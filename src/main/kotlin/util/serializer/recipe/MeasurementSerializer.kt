package zjt.projects.util.serializer.recipe

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import zjt.projects.models.account.MeasurementSystem
import zjt.projects.models.recipe.*


object MeasurementSerializer: KSerializer<Measurement> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Measurement") {
        element<String>("type")
        element<Double>("quantity")
        element<String>("measurementSystem")
        element<String>("measurementType")
        element<String>("measurementUnit")
    }

    override fun serialize(encoder: Encoder, value: Measurement) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.type.name)
            encodeDoubleElement(descriptor, 1, value.quantity)
            encodeStringElement(descriptor, 2, value.measurementSystem.name)
            encodeStringElement(descriptor, 3, value.measurementType.name)
            encodeStringElement(descriptor, 4, value.measurementUnit.toString())
        }
    }

    override fun deserialize(decoder: Decoder): Measurement {
        return decoder.decodeStructure(descriptor) {
            var type = MeasurementType.VOLUME
            var quantity = 1.0
            var measurementSystem = MeasurementSystem.IMPERIAL
            var measurementType = MeasurementType.VOLUME
            var measurementUnit: MeasurementUnit = ImperialUnit.CUP
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> type = MeasurementType.valueOf(decodeStringElement(descriptor, index))
                    1 -> quantity = decodeDoubleElement(descriptor, index)
                    2 -> measurementSystem = MeasurementSystem.valueOf(decodeStringElement(descriptor, index))
                    3 -> measurementType = MeasurementType.valueOf(decodeStringElement(descriptor, 3))
                    4 -> measurementUnit = MeasurementUnit.fromString(decodeStringElement(descriptor, 4))
                        ?: throw IllegalArgumentException("Invalid measurement unit: ${decodeStringElement(descriptor, 4)}")
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            Measurement(type, quantity, measurementSystem, measurementType, measurementUnit)
        }
    }
}