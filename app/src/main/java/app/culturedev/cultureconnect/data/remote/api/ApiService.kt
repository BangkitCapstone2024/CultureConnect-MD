package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.login.LoginRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutResponse
import app.culturedev.cultureconnect.data.response.register.RegisterRequest
import app.culturedev.cultureconnect.data.response.register.RegisterResponse
import retrofit2.http.Body
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.NotificationRes
import app.culturedev.cultureconnect.data.response.NotificationResult
import app.culturedev.cultureconnect.data.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body body: LogoutRequest
    ): LogoutResponse

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponse
    
    @GET("data")
    suspend fun getStories(
        @Header("Authorization")
        token: String
    ): DataRes

    @GET("events?active=1")
    fun getRecommendationCafe(): Call<DataRes>

    @GET("cafe?active=0")
    fun getAllCafe(): Call<DataRes>

    @GET("cafe?active=-1")
    fun searchCafe(@Query("q") query: String): Call<DataRes>

    @GET("cafe/{id}")
    fun getCafeDetail(@Path("id") id: String): Call<DataRes>

    @FormUrlEncoded
    @POST("notifications")
    suspend fun notification(
        @Body body: NotificationResult
    ) : NotificationRes

    @GET("SELECT * FROM notifications ORDER BY id DESC")
    suspend fun getAllNotifications(): List<NotificationRes>
}