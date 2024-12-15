package app.culturedev.cultureconnect.data.di

import android.content.Context
import app.culturedev.cultureconnect.data.database.AppDatabase
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.preferences.dataStore
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.helper.AppExecutor
import app.culturedev.cultureconnect.data.repository.RecommendationRepository

object Injection {
    fun provideRepository(context: Context): CafeRepo {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return CafeRepo.getInstance(apiService, pref)
    }

    fun provideRecommendationRepository(context: Context): RecommendationRepository {
        val recommendationApiService = ApiConfig.getRecommendationService()
        val pref = UserPreferences.getInstance(context.dataStore)
        val dao = AppDatabase.getDatabase(context).cafeDao()
        val appExecutor = AppExecutor()
        return RecommendationRepository.getInstance(recommendationApiService, pref, dao, appExecutor)
    }
}