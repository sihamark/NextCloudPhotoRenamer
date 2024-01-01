package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eu.heha.ncfilerenamer.ViewModel
import eu.heha.ncfilerenamer.ViewModelSomething
import eu.heha.ncfilerenamer.model.FileController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val fileController = FileController()

    var fileState: FileState? by mutableStateOf(null)
        private set

    fun loadRoot() {
        viewModelScope.launch {
            var ref = "unknown"
            try {
                ViewModelSomething.fundasda()
                ref = fileController.rootRef()
                fileState = FileState.Loading(ref)
                fileState = FileState.Files(ref, fileController.loadFileContent())
            } catch (e: Exception) {
                Napier.e("Error loading $ref", e)
                fileState = FileState.Error(ref, e.message ?: "Unknown error")
            }
        }
    }

    sealed interface FileState {
        val ref: String

        data class Loading(override val ref: String) : FileState
        data class Error(override val ref: String, val message: String) : FileState
        data class Files(override val ref: String, val files: List<FileController.Resource>) :
            FileState
    }
}