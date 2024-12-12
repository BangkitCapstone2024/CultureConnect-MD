package app.culturedev.cultureconnect.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.culturedev.cultureconnect.data.database.Dao
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.remote.api.ApiService
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.data.response.login.LoginRequest
import app.culturedev.cultureconnect.data.response.login.LoginResponse
import app.culturedev.cultureconnect.data.response.logout.LogoutRequest
import app.culturedev.cultureconnect.data.response.logout.LogoutResponse
import app.culturedev.cultureconnect.data.response.register.RegisterRequest
import app.culturedev.cultureconnect.data.response.register.RegisterResponse
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.helper.AppExecutor
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CafeRepo private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
    private val cafeDao: Dao,
    private val appExecutors: AppExecutor
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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

    fun getFavorite(): LiveData<List<DataEntity>> {
        return cafeDao.getFavorite()
    }

    fun getHistory(): LiveData<List<DataEntity>> {
        return cafeDao.getHistory()
    }

    private suspend fun insertEvent(events: List<DataEntity>) {
        cafeDao.insertFavorite(events)
    }

    fun saveEvent(cafe: List<ListDataItem>, status: Boolean) {
        val cafeList = cafe.map {
            DataEntity(
                it.id  ?: "",
                it.image ?: "",
                it.name ?: "",
                it.rating ?: "",
                it.category ?: "",
                it.address ?: "",
                it.phoneNumber ?: "",
                it.price ?: "",
                it.schedule ?: "",
                it.menu ?: "",
                false,
                status
            )
        }
        coroutineScope.launch {
            insertEvent(cafeList)
        }
    }

    suspend fun updateFavoriteStatus(cafeId: String, isFavorite: Boolean) {
        cafeDao.updateFavoriteStatus(cafeId, isFavorite)
    }

    fun isEventFavorites(cafeId: String) = liveData {
        val isFavorites = cafeDao.isFavorite(cafeId)
        emit(isFavorites)
    }

    companion object {
        @Volatile
        private var instance: CafeRepo? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences,
            eventDao: Dao,
            appExecutors: AppExecutor
        ): CafeRepo =
            instance ?: synchronized(this) {
                instance ?: CafeRepo (apiService, userPreference, eventDao, appExecutors)
            }.also { instance = it }
    }
}