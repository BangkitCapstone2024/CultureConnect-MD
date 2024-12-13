package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.repository.RecommendationRepository

class DetailViewModel(
    private val repository: RecommendationRepository
) : ViewModel()