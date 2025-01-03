package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.CafeRepo

class HistoryViewModel(
    private val repository: CafeRepo
) : ViewModel()
