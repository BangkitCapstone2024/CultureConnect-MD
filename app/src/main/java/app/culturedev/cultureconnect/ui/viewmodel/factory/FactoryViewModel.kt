package app.culturedev.cultureconnect.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.data.di.Injection
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
import app.culturedev.cultureconnect.ui.viewmodel.LoginViewModel
import app.culturedev.cultureconnect.ui.viewmodel.RegisterViewModel
import app.culturedev.cultureconnect.ui.viewmodel.SettingsViewModel

class FactoryViewModel(private val repository: CafeRepo) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown viewmodel class" + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FactoryViewModel? = null

        @JvmStatic
        fun getInstance(context: Context): FactoryViewModel {
            if (INSTANCE == null) {
                synchronized(FactoryViewModel::class.java) {
                    INSTANCE = FactoryViewModel(Injection.provideRepository(context))
                }
            }
            return INSTANCE as FactoryViewModel
        }
    }
}