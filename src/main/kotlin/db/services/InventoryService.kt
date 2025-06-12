package zjt.projects.db.services

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.models.inventory.Inventory

class InventoryService(database: MongoDatabase){

    private var collection: MongoCollection<Document>

    init {
        database.createCollection("inventories")
        collection = database.getCollection("inventories")
    }

    fun create(accountId: String){
        val inventory = Inventory(
            accountId = accountId,
            ingredients = mutableListOf()
        )
        val doc = inventory.toDocument()
        collection.insertOne(doc)
    }

    suspend fun find(id: String): Inventory? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", id)).first()?.toInventory()
    }

    suspend fun findByAccountId(accountId: String): Inventory? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("accountId", accountId)).first()?.toInventory()
    }

    suspend fun update(id: String, inventory: Inventory): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), inventory.toDocument())
    }

    suspend fun updateByAccountId(accountId: String, inventory: Inventory): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("accountId", accountId), inventory.toDocument())
    }

    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }

    suspend fun deleteByAccountId(accountId: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("accountId", accountId))
    }

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
        fun Document.toInventory(): Inventory = json.decodeFromString(this.toJson())
        fun Inventory.toDocument(): Document = Document.parse(Json.encodeToString(this))
    }
}