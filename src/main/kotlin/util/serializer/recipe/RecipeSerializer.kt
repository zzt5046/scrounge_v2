package zjt.projects.util.serializer.recipe

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import zjt.projects.models.recipe.Measurement
import zjt.projects.models.recipe.Recipe

//object RecipeSerializer : KSerializer<Recipe> {
//
//    private val ingredientsSerializer = IngredientSerializer
//    private val directionsSerializer = ListSerializer(String.serializer())
//
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Recipe") {
//        element<String>("originAccountId")
//        element<Boolean>("public")
//        element<String>("name")
//        element<String>("description")
//        element<List<Pair<Measurement,String>>>("ingredients")
//        element<List<String>>("directions")
//    }
//
//    override fun serialize(encoder: Encoder, value: Recipe) {
//
//        val jsonEncoder = encoder as? JsonEncoder
//            ?: throw SerializationException("This serializer only works with JSON")
//
//        jsonEncoder.encodeJsonElement(
//            buildJsonObject {
//                put("originAccountId", value.originAccountId)
//                put("public", value.originAccountId)
//                put("name", value.originAccountId)
//                put("description", value.originAccountId)
//                put("ingredients", Json.encodeToJsonElement(ingredientsSerializer, value.ingredients))
//                put("directions", Json.encodeToJsonElement(directionsSerializer, value.directions))
//            }
//        )
//    }
//
//    override fun deserialize(decoder: Decoder): Recipe {
//        return decoder.decodeStructure(descriptor) {
//            var originAccountId = ""
//            var public = false
//            var name = ""
//            var description = ""
//            var ingredients: List<Pair<Measurement,String>> = mutableListOf()
//            var directions: List<String> = mutableListOf()
//
//            while (true) {
//                when (val index = decodeElementIndex(descriptor)) {
//                    0 -> originAccountId = decodeStringElement(descriptor, index)
//                    1 -> public = decodeBooleanElement(descriptor, index)
//                    2 -> name = decodeStringElement(descriptor, index)
//                    3 -> description = decodeStringElement(descriptor, index)
//                    4 -> ingredients = ingredientsSerializer.deserialize(decoder)
//                    5 -> directions = directionsSerializer.deserialize(decoder)
//                    CompositeDecoder.DECODE_DONE -> break
//                    else -> error("Unexpected index: $index")
//                }
//            }
//            Recipe(originAccountId, public, name, description, ingredients, directions)
//        }
//    }
//}