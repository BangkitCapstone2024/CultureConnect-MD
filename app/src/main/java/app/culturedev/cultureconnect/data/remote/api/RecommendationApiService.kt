package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.recommendation.RecommendationRequest
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationApiService {
    @POST("predict_mood")
    suspend fun getRecommendation(
        @Body body: RecommendationRequest
    ): RecommendationResponse
}