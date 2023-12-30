package eu.heha.ncfilerenamer.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.heha.ncfilerenamer.ui.LoginViewModel.LoginState
import eu.heha.ncfilerenamer.ui.theme.AppTheme

@Composable
fun LoginScreen(
    state: LoginState,
    user: String,
    onUserChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    baseUrl: String,
    onBaseUrlChange: (String) -> Unit,
    isLoginEnabled: Boolean,
    onClickLogin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            val isTextingEnabled = state == LoginState.Idle || state is LoginState.Error
            OutlinedTextField(
                enabled = isTextingEnabled,
                value = user,
                onValueChange = onUserChange,
                label = { Text("User") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                enabled = isTextingEnabled,
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                enabled = isTextingEnabled,
                value = baseUrl,
                onValueChange = onBaseUrlChange,
                label = { Text("Base URL") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state is LoginState.Error) {
                Text(
                    state.message,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.error
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (state == LoginState.Loading) {
                CircularProgressIndicator()
            }
            Button(
                enabled = isLoginEnabled && isTextingEnabled,
                onClick = onClickLogin
            ) {
                Text("Login")
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            LoginState.Idle, "", {}, "", {}, "", {}, true, {}
        )
    }
}