package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.repository.CafeRepo
import kotlinx.coroutines.launch

class SettingsViewModel(private val cafeRepo: CafeRepo) : ViewModel() {
    fun handleLogout(session: String) = cafeRepo.handleLogout(session)
    fun logoutPreferences() = viewModelScope.launch { cafeRepo.logout() }
    fun getSession(): LiveData<UserModel> = cafeRepo.getSession().asLiveData()
}