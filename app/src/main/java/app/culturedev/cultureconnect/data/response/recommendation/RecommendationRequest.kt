package app.culturedev.cultureconnect.data.response.recommendation

import com.google.gson.annotations.SerializedName

data class RecommendationRequest(

	@field:SerializedName("text")
	val text: String? = null
)
