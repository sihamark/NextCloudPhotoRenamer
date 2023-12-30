package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eu.heha.ncfilerenamer.ViewModel
import eu.heha.ncfilerenamer.model.ConfigurationRepository
import eu.heha.ncfilerenamer.model.LoginRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var user by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var baseUrl by mutableStateOf("")
        private set

    var isLoginEnabled by mutableStateOf(false)
        private set

    var state by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun onUserChange(user: String) {
        this.user = user
        validate()
    }

    fun onPasswordChange(password: String) {
        this.password = password
        validate()
    }

    fun onBaseUrlChange(baseUrl: String) {
        this.baseUrl = baseUrl
        validate()
    }

    private fun validate() {
        isLoginEnabled = user.isNotBlank() && password.isNotBlank() && baseUrl.isNotBlank()
    }

    fun login() {
        viewModelScope.launch {
            try {
                state = LoginState.Loading
                LoginRepository().login(user, password, baseUrl)
                ConfigurationRepository.save(user, password, baseUrl)
                state = LoginState.Success
            } catch (e: Exception) {
                Napier.e("something failed", e)
                state = LoginState.Error(e.message ?: "Login failed")
            }
        }
    }

    sealed interface LoginState {
        data object Idle : LoginState
        data object Loading : LoginState
        data object Success : LoginState
        data class Error(val message: String) : LoginState
    }
}