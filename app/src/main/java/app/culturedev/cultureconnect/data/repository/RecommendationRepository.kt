package app.culturedev.cultureconnect.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.culturedev.cultureconnect.data.database.AppDatabase
import app.culturedev.cultureconnect.data.database.Dao
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.remote.api.RecommendationApiService
import app.culturedev.cultureconnect.data.response.listcafe.ListCafeResponse
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationRequest
import app.culturedev.cultureconnect.data.response.recommendation.RecommendationResponse
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.helper.AppExecutor
import retrofit2.HttpException

class RecommendationRepository(
    private val recommendationApiService: RecommendationApiService,
    private val userPreferences: UserPreferences,
    private val cafeDao: Dao,
    private val appExecutors: AppExecutor
) {

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

    // Favorite Methods

    suspend fun addFavorite(favorite: DataEntity) {
        cafeDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: DataEntity) {
        cafeDao.updateFavoriteStatus(favorite.id, false)
    }

    suspend fun updateFavoriteStatus(cafeId: String, isFavorite: Boolean) {
        cafeDao.updateFavoriteStatus(cafeId, isFavorite)
    }
    fun isFavorite(cafeId: String): Boolean {
        return cafeDao.isFavorite(cafeId)
    }

    // History Methods
    fun getHistory(): LiveData<List<DataEntity>> {
        return cafeDao.getAllHistory()
    }

    suspend fun addHistory(history: DataEntity) {
        cafeDao.insertHistory(history)
    }

    companion object {
        @Volatile
        private var instance: RecommendationRepository? = null
        fun getInstance(
            apiService: RecommendationApiService,
            userPreferences: UserPreferences,
            cafeDao: Dao,
            appExecutors: AppExecutor
        ): RecommendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecommendationRepository(apiService, userPreferences, cafeDao, appExecutors)
            }.also { instance = it }
    }
}