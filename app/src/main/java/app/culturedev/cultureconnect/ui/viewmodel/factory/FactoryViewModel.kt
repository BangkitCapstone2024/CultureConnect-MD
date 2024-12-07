package app.culturedev.cultureconnect.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.data.di.Injection
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.ui.viewmodel.DescribeMoodViewModel
import app.culturedev.cultureconnect.ui.viewmodel.DetailViewModel
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
import app.culturedev.cultureconnect.ui.viewmodel.HomeViewModel
import app.culturedev.cultureconnect.ui.viewmodel.LoginViewModel
import app.culturedev.cultureconnect.ui.viewmodel.MapsViewModel
import app.culturedev.cultureconnect.ui.viewmodel.RegisterViewModel

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

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DescribeMoodViewModel::class.java) -> {
                DescribeMoodViewModel(repository) as T
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