package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application, private val repository: CafeRepo) : AndroidViewModel(application) {
//    private val _cafeDetail = MutableLiveData<ListDataItem?>()
//    val cafeDetail: LiveData<ListDataItem?> = _cafeDetail
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    private val favoriteStatus: LiveData<Boolean> = MutableLiveData()
//
//    fun getEventDetail(id: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            delay(4000)
//            try {
//                val response = ApiConfig.getApiService().getCafeDetail(id)
//                response.enqueue(object : Callback<DataRes> {
//                    override fun onResponse(call: Call<DataRes>, response: Response<DataRes>) {
//                        _isLoading.value = false
//                        if (response.isSuccessful) {
//                            response.body()?.let {
//                                _cafeDetail.value = it.data
//                            }
//                        } else {
//                            _errorMessage.value = "Failed to Fetch API"
//                        }
//                    }
//
//                    override fun onFailure(call: Call<DataRes>, t: Throwable) {
//                        _isLoading.value = false
//                        _errorMessage.value = "Failed to Fetch API"
//                    }
//                })
//            }catch (e: Exception) {
//                _isLoading.value = false
//                _errorMessage.value = "Failed to load events: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun setFavoriteStatus(cafeId: String, isFavorite: Boolean) {
//        viewModelScope.launch {
//            repository.updateFavoriteStatus(cafeId, isFavorite)
//            (favoriteStatus as MutableLiveData).value = isFavorite
//        }
//    }
//
//    fun isEventFavorites(eventId: String): LiveData<Boolean> {
//        return repository.isEventFavorites(eventId).also {
//            it.observeForever { isFavorite ->
//                (favoriteStatus as MutableLiveData).value = isFavorite
//            }
//        }
//    }
}