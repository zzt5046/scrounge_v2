package zjt.projects.models.error

data class ScroungeError (
    val code: Int,
    val message: String,
    val displayMessage: String? = null
){
    companion object {
        val Unauthorized = ScroungeError(
            code = 401,
            message = "Unauthorized"
        )
        val ServerError = ScroungeError(
            code = 500,
            message = "Server Error"
        )
    }
}