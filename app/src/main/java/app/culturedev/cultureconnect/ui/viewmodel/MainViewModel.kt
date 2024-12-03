package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.repository.CafeRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CafeRepo) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}