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
    MainScreen(
        fileState = viewModel.fileState,
        onClickResource = viewModel::load,
        onClickReload = viewModel::reload,
        onClickNavigateBack = viewModel::navigateUp
    )
}