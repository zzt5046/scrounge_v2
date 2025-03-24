package zjt.projects.db.models.account

import java.util.UUID

data class AccountLoginResponse (
    val accountId: String?,
    val sessionId: UUID,
    val status: AccountLoginStatus
)