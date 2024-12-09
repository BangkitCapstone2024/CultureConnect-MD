package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.repository.CafeRepo

class DescribeMoodViewModel(private val repo: CafeRepo) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repo.getSession().asLiveData()
    }
}