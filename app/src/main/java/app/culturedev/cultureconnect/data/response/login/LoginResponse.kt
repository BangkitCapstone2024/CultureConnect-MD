package app.culturedev.cultureconnect.data.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("expires_at")
	val expiresAt: String? = null,

	@field:SerializedName("session_id")
	val sessionId: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
