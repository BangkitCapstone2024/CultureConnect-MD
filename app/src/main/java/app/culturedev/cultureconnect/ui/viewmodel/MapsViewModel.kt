package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.CafeRepo

class MapsViewModel (application: Application, private val repository:CafeRepo): AndroidViewModel(application) {

}