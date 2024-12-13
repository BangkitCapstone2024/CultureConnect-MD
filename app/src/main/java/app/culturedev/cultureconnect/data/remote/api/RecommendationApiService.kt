package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.listcafe.ListCafeResponse
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationRequest
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecommendationApiService {
    @POST("predict_mood")
    suspend fun getRecommendation(
        @Body body: RecommendationRequest
    ): RecommendationResponse

    @GET("get_all_cafe_data")
    suspend fun getAllCafeData(): ListCafeResponse
}