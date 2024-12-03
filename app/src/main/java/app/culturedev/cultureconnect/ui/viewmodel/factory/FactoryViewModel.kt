package app.culturedev.cultureconnect.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.data.di.Injection
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.ui.viewmodel.DetailViewModel
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
import app.culturedev.cultureconnect.ui.viewmodel.LoginViewModel
import app.culturedev.cultureconnect.ui.viewmodel.RegisterViewModel

class FactoryViewModel(private val repository: CafeRepo):
    ViewModelProvider.Factory {
}