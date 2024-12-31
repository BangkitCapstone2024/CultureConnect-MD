package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.RecommendationRepository

class MapsViewModel(private val repository: RecommendationRepository) : ViewModel() {
  fun getAllCafe() = repository.getAllCafeDate()
}