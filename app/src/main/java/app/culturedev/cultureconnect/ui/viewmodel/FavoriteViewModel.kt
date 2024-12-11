package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.helper.AppExecutor
import kotlinx.coroutines.launch

class FavoriteViewModel( private val repository: CafeRepo, private val appExecutors: AppExecutor) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeFavorite = MutableLiveData<List<DataEntity>?>()
    val listCafeFavorite: LiveData<List<DataEntity>?> = _listCafeFavorite

    init {
        favoriteEvents()
    }

    private fun favoriteEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getFavorite().observeForever {
                appExecutors.mainThread.execute {
                    _listCafeFavorite.value = it
                    _isLoading.value = false
                }
            }
        }
    }
}