package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eu.heha.ncfilerenamer.ViewModel
import eu.heha.ncfilerenamer.model.ConfigurationRepository
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    var route: Route? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            route = if (ConfigurationRepository.load() != null) {
                Route.Main
            } else {
                Route.Login
            }
        }
    }

    fun onLoginSuccess() {
        route = Route.Main
    }

    enum class Route {
        Login,
        Main
    }
}
