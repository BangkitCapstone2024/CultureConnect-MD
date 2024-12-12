package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.helper.AppExecutor
import kotlinx.coroutines.launch

class NotificationViewModel (private val repository: CafeRepo, private val appExecutors: AppExecutor) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeNotification = MutableLiveData<List<DataEntity>?>()
    val listCafeNotification: LiveData<List<DataEntity>?> = _listCafeNotification

    private var notificationCafesFromApi: List<DataEntity>? = null

    init {
        notificationCafe()
    }

    private fun notificationCafe() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getFavorite().observeForever {
                appExecutors.mainThread.execute {
                    _listCafeNotification.value = it
                    _isLoading.value = false
                }
            }
        }
    }

    fun filterNotify(query: String) {
        val filteredList = notificationCafesFromApi?.filter {
            it.name?.contains(query, ignoreCase = true) ?: false
        }
        _listCafeNotification.value = filteredList
    }

}