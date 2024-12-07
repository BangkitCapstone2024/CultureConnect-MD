package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.LoginRequest
import app.culturedev.cultureconnect.data.response.LoginResponse
import app.culturedev.cultureconnect.data.response.RegisterRes
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterRes


    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}