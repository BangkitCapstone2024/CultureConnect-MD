package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application, private val recommendationRepository: RecommendationRepository) : AndroidViewModel(application) {
    private val _cafeDetail = MutableLiveData<ListDataItem?>()
    val cafeDetail: LiveData<ListDataItem?> = _cafeDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> = _favoriteStatus
}