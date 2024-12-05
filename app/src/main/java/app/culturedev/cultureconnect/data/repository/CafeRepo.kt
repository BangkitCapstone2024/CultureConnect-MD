package app.culturedev.cultureconnect.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.remote.api.ApiService
import app.culturedev.cultureconnect.data.response.ErrorRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.data.response.LoginRes
import app.culturedev.cultureconnect.data.response.RegisterRes
import app.culturedev.cultureconnect.data.result.ResultCafe
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class CafeRepo(private val apiService: ApiService, private val userPreferences: UserPreferences) {

    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ): LiveData<ResultCafe<RegisterRes>> = liveData {
        emit(ResultCafe.Loading)
        try {
            val response = apiService.register(name = name, email = email, password = password)
            emit(ResultCafe.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorRes::class.java)
            val errorMessage = errorBody.message
            emit(ResultCafe.Error(errorMessage.toString()))
        }
    }

    fun handleLogin(email: String, password: String): LiveData<ResultCafe<LoginRes>> = liveData {
        emit(ResultCafe.Loading)
        try {
            val response = apiService.login(email = email, password = password)
            userPreferences.saveSession(
                UserModel(
                    userId = response.loginResult?.userId ?: "",
                    name = response.loginResult?.name ?: "",
                    token = response.loginResult?.token ?: ""
                )
            )
            emit(ResultCafe.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorRes::class.java)
            val errorMessage = errorBody.message
            emit(ResultCafe.Error(errorMessage.toString()))
        }
    }

    fun getStories(): LiveData<ResultCafe<List<ListDataItem>>> = liveData {
        emit(ResultCafe.Loading)
        try {
            val token = userPreferences.getToken().first()
            val response = apiService.getStories("Bearer $token")
            val stories = response.listData
            emit(ResultCafe.Success(stories))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorRes::class.java)
            val errorMessage = errorBody.message
            emit(ResultCafe.Error(errorMessage.toString()))
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
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