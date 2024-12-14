package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val repository: RecommendationRepository) : ViewModel(){
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeRecommendation = MutableLiveData<List<CafeRecommendationItem>?>()
    val listCafeRecommendation: LiveData<List<CafeRecommendationItem>?> = _listCafeRecommendation

    private val _listAllCafe = MutableLiveData<List<CafeRecommendationItem>?>()
    val listAllCafe: LiveData<List<CafeRecommendationItem>?> = _listAllCafe

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    private fun favoriteEvents(events: List<ListDataItem>, status: Boolean) {
//        viewModelScope.launch {
//            repository.saveEvent(events, status)
//        }
//    }

    fun getAllCafeData() = repository.getAllCafeDate()
}