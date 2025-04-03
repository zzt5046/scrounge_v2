package zjt.projects.util.serializer.recipe

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.*
import zjt.projects.models.recipe.Measurement

object IngredientsSerializer : KSerializer<List<Pair<Measurement, String>>> {
    private val pairSerializer = PairSerializer(MeasurementSerializer, String.serializer())
    private val listSerializer = ListSerializer(pairSerializer)

    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: List<Pair<Measurement, String>>) {
        listSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<Pair<Measurement, String>> {
        return listSerializer.deserialize(decoder)
    }
}