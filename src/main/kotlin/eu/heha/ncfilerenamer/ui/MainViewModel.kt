package eu.heha.ncfilerenamer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eu.heha.ncfilerenamer.ViewModel
import eu.heha.ncfilerenamer.model.FileController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val fileController = FileController()

    var fileState: FileState? by mutableStateOf(null)
        private set

    fun loadRoot() {
        load()
    }

    fun load(resource: FileController.Resource) {
        if (!resource.isDirectory) {
            Napier.e("clicked, resource ${resource.ref}, which is not a directory")
            return
        }
        load(resource.ref)
    }

    private fun load(ref: String? = null) {
        Napier.e("Loading $ref")
        viewModelScope.launch {
            var actualRef = ref ?: "unknown"
            try {
                actualRef = ref ?: fileController.rootRef()
                fileState = FileState.Loading(actualRef)
                fileState = FileState.Files(actualRef, fileController.loadFileContent(actualRef))
            } catch (e: Exception) {
                Napier.e("Error loading $ref", e)
                fileState = FileState.Error(actualRef, e.message ?: "Unknown error")
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