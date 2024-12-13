package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (application: Application, private val repository: RecommendationRepository) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeRecommendation = MutableLiveData<List<ListDataItem>?>()
    val listCafeRecommendation: LiveData<List<ListDataItem>?> = _listCafeRecommendation

    private val _listAllCafe = MutableLiveData<List<ListDataItem>?>()
    val listAllCafe: LiveData<List<ListDataItem>?> = _listAllCafe

    init {
        recommendationCafe()
        allCafe()
    }

    private fun recommendationCafe() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(4000)
            try {
                val result = ApiConfig.getApiService().getRecommendationCafe()
                result.enqueue(object : Callback<DataRes> {
                    override fun onResponse(call: Call<DataRes>, response: Response<DataRes>) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _listCafeRecommendation.value = response.body()?.listData
                        } else {
                            launch(Dispatchers.Main) {
                                Toast.makeText(getApplication(), "Failed to Fetch API", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DataRes>, t: Throwable) {
                        launch(Dispatchers.Main) {
                            Toast.makeText(getApplication(), "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun allCafe() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(4000)
            try {
                val result = ApiConfig.getApiService().getAllCafe()
                result.enqueue(object : Callback<DataRes> {
                    override fun onResponse(call: Call<DataRes>, response: Response<DataRes>) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            val event = response.body()?.listData
                            _listAllCafe.value = event?.take(10)
                        } else {
                            launch(Dispatchers.Main) {
                                Toast.makeText(getApplication(), "Failed to Fetch API", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DataRes>, t: Throwable) {
                        launch(Dispatchers.Main) {
                            Toast.makeText(getApplication(), "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private fun favoriteEvents(events: List<ListDataItem>, status: Boolean) {
        viewModelScope.launch {
            repository.saveEvent(events, status)
        }
    }

    fun getAllCafeData() = repository.getAllCafeDate()
}