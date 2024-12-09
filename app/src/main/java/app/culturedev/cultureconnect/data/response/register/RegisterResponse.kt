package app.culturedev.cultureconnect.data.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("session_id")
    val sessionId: String? = null,

    @field:SerializedName("expires_at")
    val expiresAt: String? = null

)
