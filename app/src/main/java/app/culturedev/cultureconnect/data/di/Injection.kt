package app.culturedev.cultureconnect.data.di

import android.content.Context
import app.culturedev.cultureconnect.data.preferences.UserPreferences
import app.culturedev.cultureconnect.data.preferences.dataStore
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo

object Injection {
    fun provideRepository(context: Context): CafeRepo {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return CafeRepo.getInstance(apiService, pref)
    }
}