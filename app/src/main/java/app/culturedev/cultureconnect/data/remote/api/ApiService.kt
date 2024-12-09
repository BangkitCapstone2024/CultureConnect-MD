package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.login.LoginRequest
import app.culturedev.cultureconnect.data.response.login.LoginResponse
import app.culturedev.cultureconnect.data.response.logout.LogoutRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutResponse
import app.culturedev.cultureconnect.data.response.register.RegisterRequest
import app.culturedev.cultureconnect.data.response.register.RegisterResponse
import retrofit2.http.Body
import androidx.room.Insert
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.LoginRes
import app.culturedev.cultureconnect.data.response.NotificationRes
import app.culturedev.cultureconnect.data.response.RegisterRes
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginRes

    @POST("auth/logout")
    suspend fun logout(
        @Body body: LogoutRequest
    ): LogoutResponse

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterRes
    
    @GET("data")
    suspend fun getStories(
        @Header("Authorization")
        token: String
    ): DataRes

    @FormUrlEncoded
    @POST("notifications")
    suspend fun notification(
        @Field("title") title: String,
        @Field("content") message: String,
        @Field("time") time: String,
        @Field("image") icon: String
    ) : NotificationRes

    @GET("SELECT * FROM notifications ORDER BY id DESC")
    suspend fun getAllNotifications(): List<NotificationRes>
}