package app.culturedev.cultureconnect.data.response.logout

import com.google.gson.annotations.SerializedName

data class LogoutRequest(

	@field:SerializedName("session-id")
	val sessionId: String? = null
)
