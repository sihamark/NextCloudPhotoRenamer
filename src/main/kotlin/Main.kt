import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
@Preview
fun App() {
    val model = remember { ViewModelSomething() }
    DisposableEffect(model) {
        onDispose { model.close() }
    }

    MaterialTheme {
        Button(onClick = { model.something() }) {
            Text("do stuff")
        }
    }
}

fun main() = application {
    Napier.base(DebugAntilog())
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
