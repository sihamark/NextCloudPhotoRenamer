package eu.heha.ncfilerenamer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import eu.heha.ncfilerenamer.model.Logging
import eu.heha.ncfilerenamer.ui.App
import eu.heha.ncfilerenamer.ui.theme.AppTheme
import io.github.aakira.napier.Napier

fun application() = application {
    Napier.base(Logging.antilog())
    Window(
        onCloseRequest = ::exitApplication,
        title = "NextCloud Photo Renamer",
        icon = painterResource("logo_nextcloud.svg")
    ) {
        AppTheme {
            App()
        }
    }
}