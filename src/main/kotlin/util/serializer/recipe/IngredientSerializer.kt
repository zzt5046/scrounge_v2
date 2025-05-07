package zjt.projects.util.serializer.recipe

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.*
import zjt.projects.models.recipe.Ingredient

//object IngredientSerializer : KSerializer<List<Ingredient>> {
//    //private val listSerializer = ListSerializer()
//
//    override val descriptor: SerialDescriptor = listSerializer.descriptor
//
//    override fun serialize(encoder: Encoder, value: List<Ingredient>) {
//        listSerializer.serialize(encoder, value)
//    }
//
//    override fun deserialize(decoder: Decoder): List<Ingredient> {
//        return listSerializer.deserialize(decoder)
//    }
//}