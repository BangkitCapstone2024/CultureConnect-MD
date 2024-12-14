package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendationItem

class SharedViewModel : ViewModel() {
    private val _cafeRecommendations = MutableLiveData<List<CafeRecommendationItem>>()
    val cafeRecommendations: LiveData<List<CafeRecommendationItem>> get() = _cafeRecommendations

    fun setCafeRecommendations(recommendations: List<CafeRecommendationItem>) {
        _cafeRecommendations.value = recommendations
    }
}