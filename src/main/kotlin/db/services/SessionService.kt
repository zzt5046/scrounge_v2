package zjt.projects.db.services

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json
import org.bson.Document
import zjt.projects.config.UserSession

class SessionService(database: MongoDatabase) {
    private var collection: MongoCollection<Document>

    init {
        database.createCollection("sessions")
        collection = database.getCollection("sessions")
    }

//    fun createSession(call: RoutingCall, accountId: String) {
//        if(!sessionExists(accountId)){
//            val doc = session.toDocument()
//            collection.insertOne(doc)
//        }else{
//            deleteSession(accountId)
//            createSession(call, accountId)
//        }
//    }

    private fun sessionExists(accountId: String) =  findSession(accountId) != null

    fun findSession(accountId: String): UserSession? =
        collection.find(Filters.eq("accountId", accountId)).first()?.toUserSession()

    fun deleteSession(accountId: String) = collection.findOneAndDelete(Filters.eq("accountID", accountId))


    companion object{
        private val json = Json { ignoreUnknownKeys = true }
        fun Document.toUserSession(): UserSession = json.decodeFromString(this.toJson())
        fun UserSession.toDocument(): Document = Document.parse(Json.encodeToString(this))
    }
}