package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import eu.heha.ncfilerenamer.viewModel

@Composable
fun MainRoute() {
    val viewModel = viewModel { MainViewModel() }
    LaunchedEffect(viewModel) {
        viewModel.loadRoot()
    }
    MainScreen(viewModel.fileState)
}