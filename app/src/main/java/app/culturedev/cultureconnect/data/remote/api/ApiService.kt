package app.culturedev.cultureconnect.data.remote.api

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
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterRes

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginRes

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