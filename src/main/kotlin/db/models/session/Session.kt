package zjt.projects.db.models.session

import java.time.OffsetDateTime
import java.util.*

class Session (
    val sessionId: UUID,
    val expireDate: OffsetDateTime
)