package eu.heha.ncfilerenamer.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.heha.ncfilerenamer.ui.LoginViewModel.LoginState
import eu.heha.ncfilerenamer.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
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
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
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