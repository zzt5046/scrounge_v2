package zjt.projects.db.operations

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import zjt.projects.db.models.account.*

class AccountService(database: MongoDatabase) {
    private var collection: MongoCollection<Document>

    init {
        database.createCollection("accounts")
        collection = database.getCollection("accounts")
    }

    suspend fun authenticate(request: AccountLoginRequest): AccountLoginStatus {
        readByUsername(request.userName)?.let {
            return if (it.credentials == request.credentials) AccountLoginStatus.SUCCESS else AccountLoginStatus.FAILURE
        }

        return AccountLoginStatus.FAILURE
    }

    suspend fun create(request: AccountCreateRequest): String = withContext(Dispatchers.IO) {
        val account = Account(
            userName = request.userName,
            credentials = request.credentials,
            emailAddress = request.emailAddress,
            securityQuestionId = request.securityQuestionId,
            securityQuestionAnswer = request.securityQuestionAnswer,
            preferences = defaultAccountPreferences(),
        )
        val doc = account.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    suspend fun read(id: String): Account? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(Account.Companion::fromDocument)
    }

    suspend fun readByUsername(userName: String): Account? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("userName", userName)).first()?.let(Account.Companion::fromDocument)
    }

    suspend fun update(id: String, account: Account): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), account.toDocument())
    }

    suspend fun updateAccountPreferences(id: String, accountPreferences: AccountPreferences): Document? = withContext(Dispatchers.IO) {
        val account = read(id)?.copy(
            preferences = accountPreferences.value()
        )
        account?.toDocument()?.let { collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), it) }
    }

    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }
}

