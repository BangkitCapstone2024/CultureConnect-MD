package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.data.database.FavoriteData
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: RecommendationRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCafeFavorite = MutableLiveData<List<DataEntity>?>()
    val listCafeFavorite: LiveData<List<DataEntity>?> = _listCafeFavorite


    fun addFavorite(favorite: FavoriteData) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: FavoriteData) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }

    fun searchFavorites(query: String): LiveData<List<FavoriteData>> {
        return repository.searchFavorites(query)
    }
}
