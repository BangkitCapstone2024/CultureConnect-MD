package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.LoginRes
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
}