package eu.heha.ncfilerenamer

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import eu.heha.ncfilerenamer.ui.LoginRoute
import eu.heha.ncfilerenamer.ui.theme.AppTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
@Preview
fun App() {
    AppTheme {
        LoginRoute(onSuccess = { Napier.e { "Login successfull" } })
    }
}

fun application() = application {
    Napier.base(DebugAntilog())
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}