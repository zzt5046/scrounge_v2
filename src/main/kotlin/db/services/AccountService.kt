package zjt.projects.db.operations

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.db.services.InventoryService
import zjt.projects.db.services.RecipeService
import zjt.projects.models.account.*
import zjt.projects.models.account.Account.Companion.toAccountResponse
import zjt.projects.models.account.settings.*
import zjt.projects.models.recipe.RecipeResponse
import zjt.projects.util.crypto.generateSalt
import zjt.projects.util.crypto.sha256

class AccountService {
    private lateinit var collection: MongoCollection<Document>

    private lateinit var database: MongoDatabase
    private lateinit var recipeService: RecipeService
    private lateinit var inventoryService: InventoryService

    fun init(
        db: MongoDatabase,
        recipeService: RecipeService,
        inventoryService: InventoryService
    ) {
        this.database = db
        this.recipeService = recipeService
        this.inventoryService = inventoryService

        database.createCollection("accounts")
        collection = database.getCollection("accounts")
    }

    // Account Login / Logout
    // -----------------------------------------------------------------------------------------------------------
    suspend fun authenticate(request: AccountLoginRequest): AccountLoginResponse {

        val accountDoc = readDocument(accountId = null, userName = request.userName)
        return if(accountDoc != null){
            val accountId = accountDoc["_id"].toString()
            val account = accountDoc.toAccount()
            val salt = account.salt

            if (accountDoc["password"] == (salt + request.password).sha256()){
                AccountLoginResponse(
                    accountId = accountId,
                    account = account.toAccountResponse(),
                    status = AccountLoginStatus.SUCCESS,
                    settings = account.settings,
                    inventory = getInventory(accountId),
                    recipes = getRecipes(accountId),
                )
            }else{
                loginFailedResponse
            }
        }else{
            loginFailedResponse
        }
    }

    private suspend fun getInventory(accountId: String): List<String>? =
        inventoryService.findByAccountId(accountId)?.ingredients

    private fun getRecipes(accountId: String): List<RecipeResponse> =
        recipeService.getRecipesByAccountId(accountId).recipes


    // CRUD
    // -----------------------------------------------------------------------------------------------------------

    // CREATE
    // ------------------------------------------
    suspend fun create(request: AccountCreateRequest): String = withContext(Dispatchers.IO) {
        val salt = generateSalt()
        val account = Account(
            userName = request.userName,
            password = (salt + request.password).sha256(),
            salt = salt,
            emailAddress = request.emailAddress,
            securityQuestionId = request.securityQuestionId,
            securityQuestionAnswer = (salt + request.securityQuestionAnswer).sha256(),
            settings = defaultAccountSettings(),
            favoriteRecipes = mutableSetOf()
        )
        val doc = account.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }


    // READ
    // ------------------------------------------
    private suspend fun read(id: String): Account? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.toAccount()
    }
    private suspend fun readByUsername(userName: String): Account? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("userName", userName)).first()?.toAccount()
    }
    private fun readDocument(accountId: String?, userName: String?): Document?{
        require (!(accountId.isNullOrEmpty() && userName.isNullOrEmpty())){
            throw IllegalArgumentException("Must supply accountId or userName when retrieving Account Document")
        }
        return if(accountId.isNullOrEmpty()){
            collection.find(Filters.eq("userName", userName)).first()
        }else{
            collection.find(Filters.eq("_id", ObjectId(accountId))).first()
        }
    }

    suspend fun accountExists(accountId: String): Boolean =
        read(accountId) != null

    suspend fun findFullAccount(accountId: String): Account? =
        read(accountId)

    suspend fun findAccount(accountId: String): AccountResponse? =
        read(accountId).toAccountResponse()

    suspend fun findAccountByUsername(userName: String): AccountResponse? =
        readByUsername(userName).toAccountResponse()

    fun getAccountIdByUserName(userName: String): String? =
        readDocument(accountId = null, userName = userName)?.let {
            document ->  return document["_id"] as String
        }

    suspend fun getAccountSettings(accountId: String): AccountSettingsResponse {
        val account = findAccount(accountId)
        return account?.settings?.toResponse() ?: defaultAccountSettings().toResponse()
    }

    // UPDATE
    // ------------------------------------------
    suspend fun update(id: String, account: Account): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), account.toDocument())
    }

    // DELETE
    // ------------------------------------------
    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }

    // END CRUD --------------------------------------------------------------------------------------------------
    companion object {
        private val json = Json { ignoreUnknownKeys = true }
        fun Document.toAccount(): Account = json.decodeFromString(this.toJson())
        fun Account.toDocument(): Document = Document.parse(Json.encodeToString(this))
        val loginFailedResponse = AccountLoginResponse(
            status = AccountLoginStatus.FAILURE,
            accountId = null,
            account = null,
            settings = null,
            recipes = null,
            inventory = null
        )
    }
}

