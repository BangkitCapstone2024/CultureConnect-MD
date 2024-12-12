package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.RecommendationRepository

class DescribeMoodViewModel(private val repo: RecommendationRepository) : ViewModel() {
    fun sendUserMood(mood: String) = repo.sendUserMood(mood)
}