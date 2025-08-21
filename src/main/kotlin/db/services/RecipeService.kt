package zjt.projects.db.services

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.db.operations.AccountService
import zjt.projects.models.recipe.*

class RecipeService {

    private lateinit var collection: MongoCollection<Document>

    private lateinit var database: MongoDatabase
    private lateinit var accountService: AccountService

    fun init(database: MongoDatabase, accountService: AccountService){
        this.database = database
        this.accountService = accountService

        database.createCollection("recipes")
        collection = database.getCollection("recipes")
    }

    suspend fun createRecipe(recipe: Recipe): String = withContext(Dispatchers.IO) {
        if(accountService.accountExists(recipe.accountId)){
            val doc = recipe.toDocument()
            collection.insertOne(doc)
            doc["_id"].toString()
        }else{
            throw IllegalAccessException("Provided account ID does not exist")
        }
    }

    fun getPublicRecipes(): RecipesResponse =
        collection.find(Filters.eq("public", true)).toRecipesResponse()

    fun getRecipeById(id: String): Recipe? =
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.toRecipe()

    fun getRecipesByIds(ids: Set<String>): RecipesResponse =
        collection.find(Filters.`in`("_id", ids.map { ObjectId(it) })).toRecipesResponse()

    fun getRecipesByAccountId(id: String): RecipesResponse =
        collection.find(Filters.eq("accountId", id)).toRecipesResponse()

    suspend fun updateRecipe(id: String, recipe: Recipe): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), recipe.toDocument())
    }

    suspend fun deleteRecipe(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
        fun Document.toRecipe(): Recipe = json.decodeFromString(this.toJson())
        fun Recipe.toDocument(): Document = Document.parse(Json.encodeToString(this))
        private fun Document.toRecipeResponse(): RecipeResponse =
            RecipeResponse(
                recipeId = this["_id"].toString(),
                recipe = this.toRecipe()
            )
        fun FindIterable<Document>.toRecipesResponse(): RecipesResponse{
            val recipeResponses = mutableListOf<RecipeResponse>()
            for(document in this){
                recipeResponses.add(document.toRecipeResponse())
            }
            return RecipesResponse(recipeResponses)
        }

    }
}