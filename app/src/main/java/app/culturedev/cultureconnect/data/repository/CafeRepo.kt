package app.culturedev.cultureconnect.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.remote.api.ApiService
import app.culturedev.cultureconnect.data.response.login.LoginRequest
import app.culturedev.cultureconnect.data.response.login.LoginResponse
import app.culturedev.cultureconnect.data.response.logout.LogoutRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutResponse
import app.culturedev.cultureconnect.data.response.register.RegisterRequest
import app.culturedev.cultureconnect.data.response.register.RegisterResponse
import app.culturedev.cultureconnect.data.result.ResultCafe
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class CafeRepo private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ): LiveData<ResultCafe<RegisterResponse>> = liveData {
        emit(ResultCafe.Loading)
        try {
            val request = RegisterRequest(name, email, password)
            val response = apiService.register(request)
            userPreferences.saveSession(
                UserModel(
                    username = request.username ?: "",
                    sessionId = response.sessionId ?: "",
                    email = email
                )
            )
            emit(ResultCafe.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultCafe.Error(errorMessage.toString()))
        }
    }

    fun handleLogout(session: String): LiveData<ResultCafe<LogoutResponse>> =
        liveData {
            emit(ResultCafe.Loading)
            try {
                val request = LogoutRequest(session)
                val response = apiService.logout(request)
                emit(ResultCafe.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LogoutResponse::class.java)
                val errorMessage = errorBody.message
                emit(ResultCafe.Error(errorMessage.toString()))
            }
        }

    fun handleLogin(username: String, password: String): LiveData<ResultCafe<LoginResponse>> =
        liveData {
            emit(ResultCafe.Loading)
            try {
                val request = LoginRequest(username, password)
                val response = apiService.login(request)
                userPreferences.saveSession(
                    UserModel(
                        username = request.username ?: "",
                        sessionId = response.sessionId ?: ""
                    )
                )
                emit(ResultCafe.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                emit(ResultCafe.Error(errorMessage.toString()))
            }
        }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        @Volatile
        private var instance: CafeRepo? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences
        ): CafeRepo =
            instance ?: synchronized(this) {
                instance ?: CafeRepo(apiService, userPreference)
            }.also { instance = it }
    }
}