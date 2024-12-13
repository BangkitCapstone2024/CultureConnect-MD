package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.repository.RecommendationRepository

class HomeViewModel(private val repository: RecommendationRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getAllCafeData() = repository.getAllCafeDate()
}