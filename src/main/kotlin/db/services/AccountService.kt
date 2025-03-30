package zjt.projects.db.operations

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.models.account.*
import zjt.projects.models.account.Account.Companion.toAccountResponse
import zjt.projects.db.services.SessionService

class AccountService(database: MongoDatabase) {
    private var collection: MongoCollection<Document>
    private var sessionService = SessionService(database)

    init {
        database.createCollection("accounts")
        collection = database.getCollection("accounts")
    }

    // Account Login / Logout
    // -----------------------------------------------------------------------------------------------------------
    suspend fun authenticate(request: AccountLoginRequest): AccountLoginResponse {
        val document = readDocument(null, userName = request.userName)
        val status = document?.let {
            if (it["credentials"] == request.credentials) AccountLoginStatus.SUCCESS else AccountLoginStatus.FAILURE
        } ?: AccountLoginStatus.FAILURE

        return if (status == AccountLoginStatus.SUCCESS){
            val accountId = document?.get("_id").toString()
            AccountLoginResponse(
                accountId = accountId,
                status = AccountLoginStatus.SUCCESS,
            )
        }else{
            AccountLoginResponse(
                accountId = null,
                status = AccountLoginStatus.FAILURE
            )
        }
    }

    fun logout(accountId: String){
        sessionService.deleteSession(accountId = accountId)
    }
    // -----------------------------------------------------------------------------------------------------------


    // CRUD
    // -----------------------------------------------------------------------------------------------------------

    // CREATE
    // ------------------------------------------
    suspend fun create(request: AccountCreateRequest): String = withContext(Dispatchers.IO) {
        val account = Account(
            userName = request.userName,
            credentials = request.credentials,
            emailAddress = request.emailAddress,
            securityQuestionId = request.securityQuestionId,
            securityQuestionAnswer = request.securityQuestionAnswer,
            settings = defaultAccountSettings(),
        )
        val doc = account.toDocument()
        collection.insertOne(account.toDocument())
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

    suspend fun findAccount(accountId: String): AccountResponse? =
        read(accountId).toAccountResponse()

    suspend fun findAccountByUsername(userName: String): AccountResponse? =
        readByUsername(userName).toAccountResponse()

    fun getAccountSettings(accountId: String): AccountSettingsResponse =
        defaultAccountSettings().toResponse()



    // UPDATE
    // ------------------------------------------
    suspend fun update(id: String, account: Account): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), account.toDocument())
    }

    suspend fun updateAccountSettings(id: String, accountSettings: AccountSettings): Document? = withContext(Dispatchers.IO) {
        val account = read(id)?.copy(
            settings = accountSettings.value()
        )
        account?.toDocument()?.let { collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), it) }
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
    }
}

