package zjt.projects.services

import io.ktor.http.*
import org.bson.Document
import zjt.projects.db.models.session.Session
import java.time.OffsetDateTime
import java.util.*

class SessionService {
    private val sessionMap = mutableMapOf<String, Session>()

    fun createSession(accountId: String): UUID? {
        return if(!sessionExists(accountId)){
            val sessionId = UUID.randomUUID()
            sessionMap[accountId] = Session(
                sessionId = sessionId,
                expireDate = OffsetDateTime.now().plusMinutes(15)
            )
            println(sessionMap.entries)
            sessionId
        }else{
            deleteSession(accountId)
            createSession(accountId)
        }
    }

    private fun sessionExists(accountId: String) = sessionMap[accountId] != null

    fun sessionValid(sessionId: UUID, accountId: String): Boolean {
        val sessionValidForAccount = sessionMap[accountId]?.sessionId == sessionId
        val sessionNotExpired = sessionMap[accountId]?.expireDate?.isAfter(OffsetDateTime.now()) ?: false
        return sessionValidForAccount && sessionNotExpired
    }

    fun clean(){
        sessionMap.entries.forEach {
            if(it.value.expireDate.isBefore(OffsetDateTime.now())) deleteSession(it.key)
        }
    }

    fun deleteSession(accountId: String) = sessionMap.remove(accountId)

    fun getSessionCookie(doc: Document?): Cookie {
        //If running in an actual env, we'll need to change secure, domain, and extensions
        return Cookie(
            name = COOKIE_HEADER,
            value = doc?.get("_id")?.let { createSession(it.toString()) }.toString(),
            maxAge = 900,
            path = "/",
            secure = false,
            domain = "localhost",
            extensions = mapOf("SameSite" to "Lax")
        )
    }

    companion object{
        const val COOKIE_HEADER = "scrounge-session-id"
    }
}