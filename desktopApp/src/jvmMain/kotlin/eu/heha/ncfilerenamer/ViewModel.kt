package eu.heha.ncfilerenamer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class ViewModel {
    val viewModelScope = MainScope()

    open fun onCleared() {
        viewModelScope.cancel()
    }
}

@Composable
inline fun <reified VM : ViewModel> viewModel(crossinline creator: @DisallowComposableCalls () -> VM): VM {
    val model = remember { creator() }
    DisposableEffect(model) {
        onDispose { model.onCleared() }
    }
    return model
}