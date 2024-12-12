package app.culturedev.cultureconnect.data.model

data class UserModel (
    val username:String,
    val sessionId: String,
    val email: String? = null
)