package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import app.culturedev.cultureconnect.data.repository.CafeRepo

class RegisterViewModel(private val repository: CafeRepo) :
    ViewModel() {
    fun handleRegister(username: String, email: String, password: String) = repository.handleRegistration(username, email, password)
}