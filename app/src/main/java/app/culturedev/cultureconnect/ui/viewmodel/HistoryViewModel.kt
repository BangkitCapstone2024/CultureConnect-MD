package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.helper.AppExecutor
import kotlinx.coroutines.launch

class HistoryViewModel (private val repository: CafeRepo, private val appExecutors: AppExecutor) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeHistory = MutableLiveData<List<DataEntity>?>()
    val listCafeHistory: LiveData<List<DataEntity>?> = _listCafeHistory

    private var historyCafesFromApi: List<DataEntity>? = null

    init {
        historyCafe()
    }

    private fun historyCafe() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getFavorite().observeForever {
                appExecutors.mainThread.execute {
                    _listCafeHistory.value = it
                    _isLoading.value = false
                }
            }
        }
    }

    fun filterHistory(query: String) {
        val filteredList = historyCafesFromApi?.filter {
            it.name?.contains(query, ignoreCase = true) ?: false
        }
        _listCafeHistory.value = filteredList
    }
}