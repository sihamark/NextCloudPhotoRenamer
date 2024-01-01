package eu.heha.ncfilerenamer.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.heha.ncfilerenamer.viewModel

@Composable
fun App() {
    val model = viewModel { AppViewModel() }
    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(model.route, contentAlignment = Alignment.Center) { route ->
                when (route) {
                    AppViewModel.Route.Login -> LoginRoute(onSuccess = { model.onLoginSuccess() })
                    AppViewModel.Route.Main -> MainRoute()
                    else -> Unit
                }
            }
        }
    }
}
