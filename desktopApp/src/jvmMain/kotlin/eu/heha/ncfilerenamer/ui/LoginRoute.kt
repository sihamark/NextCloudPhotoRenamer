package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import eu.heha.ncfilerenamer.ui.LoginViewModel.LoginState.Success
import eu.heha.ncfilerenamer.viewModel

@Composable
fun LoginRoute(onSuccess: () -> Unit) {
    val model = viewModel { LoginViewModel() }
    LaunchedEffect(model.state) {
        if (model.state == Success) onSuccess()
    }
    LoginScreen(
        state = model.state,
        user = model.user,
        password = model.password,
        baseUrl = model.baseUrl,
        onUserChange = model::onUserChange,
        onPasswordChange = model::onPasswordChange,
        onBaseUrlChange = model::onBaseUrlChange,
        isLoginEnabled = model.isLoginEnabled,
        onClickLogin = model::login
    )
}