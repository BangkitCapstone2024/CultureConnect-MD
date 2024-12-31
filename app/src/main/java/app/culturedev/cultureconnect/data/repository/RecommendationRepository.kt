package app.culturedev.cultureconnect.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.culturedev.cultureconnect.data.database.AppDatabase
import app.culturedev.cultureconnect.data.database.FavoriteDao
import app.culturedev.cultureconnect.data.database.FavoriteData
import app.culturedev.cultureconnect.data.database.HistoryDao
import app.culturedev.cultureconnect.data.database.HistoryData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.remote.api.RecommendationApiService
import app.culturedev.cultureconnect.data.response.listcafe.ListCafeResponse
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationRequest
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationResponse
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.helper.AppExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.HttpException

class RecommendationRepository(
    private val recommendationApiService: RecommendationApiService,
    private val userPreferences: UserPreferences,
    private val cafeDao: AppDatabase,
    private val favoriteDao: FavoriteDao,
    private val historyDao: HistoryDao,
    private val appExecutors: AppExecutor
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun sendUserMood(userMood: String): LiveData<ResultCafe<RecommendationResponse>> =
        liveData {
            emit(ResultCafe.Loading)
            try {
                val request = RecommendationRequest(userMood)
                val response = recommendationApiService.getRecommendation(request)
                emit(ResultCafe.Success(response))
            } catch (e: HttpException) {
                val errorMessage = "Error send user mood data"
                emit(ResultCafe.Error(errorMessage))
            }
        }

    fun getSession(): kotlinx.coroutines.flow.Flow<UserModel> {
        return userPreferences.getSession()
    }

    fun getAllCafeDate(): LiveData<ResultCafe<ListCafeResponse>> =
        liveData {
            emit(ResultCafe.Loading)
            try {
                val response = recommendationApiService.getAllCafeData()
                emit(ResultCafe.Success(response))
            } catch (e: HttpException) {
                val errorMessage = "Error get all cafe data"
                emit(ResultCafe.Error(errorMessage))
            }
        }

    fun getFavorite(): LiveData<List<FavoriteData>> {
        return favoriteDao.getAllFavorites()
    }

    fun getHistory(): LiveData<List<HistoryData>> {
        return historyDao.getAllHistory()
    }

    suspend fun addFavorite(favorite: FavoriteData) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: FavoriteData) {
        favoriteDao.deleteFavorite(favorite)
    }

    fun searchFavorites(query: String): LiveData<List<FavoriteData>> {
        return favoriteDao.searchFavorites(query)
    }

    suspend fun addHistory(history: HistoryData) {
        historyDao.insertHistory(history)
    }

    fun searchHistory(query: String): LiveData<List<HistoryData>> {
        return historyDao.searchHistory(query)
    }

    companion object {
        @Volatile
        private var instance: RecommendationRepository? = null
        fun getInstance(
            apiService: RecommendationApiService,
            userPreferences: UserPreferences,
            cafeDao: AppDatabase,
            favoriteDao: FavoriteDao,
            historyDao: HistoryDao,
            appExecutors: AppExecutor
        ): RecommendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecommendationRepository(apiService, userPreferences, cafeDao, favoriteDao, historyDao, appExecutors)
            }.also { instance = it }
    }
}