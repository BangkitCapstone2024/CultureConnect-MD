package app.culturedev.cultureconnect.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.data.di.Injection
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.ui.viewmodel.DescribeMoodViewModel

class RecommendationFactoryViewModel(private val repository: RecommendationRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DescribeMoodViewModel::class.java) -> {
                DescribeMoodViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown viewmodel class " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecommendationFactoryViewModel? = null

        @JvmStatic
        fun getInstance(context: Context): RecommendationFactoryViewModel {
            if (INSTANCE == null) {
                synchronized(RecommendationFactoryViewModel::class.java) {
                    INSTANCE = RecommendationFactoryViewModel(
                        Injection.provideRecommendationRepository(context)
                    )
                }
            }
            return INSTANCE as RecommendationFactoryViewModel
        }
    }
}