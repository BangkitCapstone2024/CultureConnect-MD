package app.culturedev.cultureconnect.data.remote.api

import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.login.LoginRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutResponse
import app.culturedev.cultureconnect.data.response.register.RegisterRequest
import app.culturedev.cultureconnect.data.response.register.RegisterResponse
import retrofit2.http.Body
import app.culturedev.cultureconnect.data.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/get_all_cafe_data/{title}")
    fun getCafeDetail(@Path("Title") id: String): Call<DataRes>
}