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
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCafeViewModel (application: Application, private val repository: CafeRepo) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "AllCafeViewModel"
    }
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _listAllCafe = MutableLiveData<List<ListDataItem>?>()
    val listAllCafe: LiveData<List<ListDataItem>?> = _listAllCafe

    private var allCafesFromApi: List<ListDataItem>? = null

    init {
        fetchAllCafe()
    }

    private fun fetchAllCafe() {
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
                            Toast.makeText(getApplication(), "Failed to Fetch API", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DataRes>, t: Throwable) {
                        Toast.makeText(getApplication(), "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
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

    fun filterCafes(query: String) {
        val filteredList = allCafesFromApi?.filter {
            it.name?.contains(query, ignoreCase = true) ?: false
        }
        _listAllCafe.value = filteredList
    }
}