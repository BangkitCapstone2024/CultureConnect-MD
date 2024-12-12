package app.culturedev.cultureconnect.data.di

import android.content.Context
import app.culturedev.cultureconnect.data.database.AppDatabase
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.preferences.dataStore
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.helper.AppExecutor

object Injection {
    fun provideRepository(context: Context): CafeRepo {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
//        val database = AppDatabase.getDatabase(context)
//        val dao = database.cafeDao()
//        val appExecutors = AppExecutor()
        return CafeRepo.getInstance(apiService, pref)
    }

    fun provideRecommendationRepository(context: Context): RecommendationRepository {
        val recommendationApiService = ApiConfig.getRecommendationService()
        return RecommendationRepository.getInstance(recommendationApiService)
    }
}