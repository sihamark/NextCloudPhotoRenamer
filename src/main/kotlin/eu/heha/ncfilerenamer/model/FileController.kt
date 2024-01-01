package eu.heha.ncfilerenamer.model

import com.github.sardine.Sardine
import com.github.sardine.SardineFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URI

class FileController {

    private lateinit var configuration: ConfigurationRepository.Configuration
    private lateinit var _sardine: Sardine

    private suspend fun sardine(): Sardine {
        if (!::_sardine.isInitialized) {
            withContext(IO) {
                val config = ConfigurationRepository.load()
                    ?: error("configuration not present")
                configuration = config
                _sardine = SardineFactory.begin(config.password, config.password)
            }
        }
        return _sardine
    }

    suspend fun rootRef(): String {
        sardine()
        return "files/${configuration.user}"
    }

    suspend fun loadFileContent(ref: URI? = null): List<Resource> = withContext(IO) {
        val uri = uri(ref)
        Napier.e { "Loading from $uri" }
        sardine().list(uri)
            .filter {
                it.href != ref
            }
            .map { davResource ->
                Resource(
                    ref = davResource.href.toString(),
                    name = davResource.name,
                    isDirectory = davResource.isDirectory,
                    isRoot = ref == null
                )
            }
    }

    private fun uri(ref: URI?): String = if (ref == null) {
        "${configuration.baseUrl}remote.php/dav/files/${configuration.user}"
    } else {
        "${configuration.baseUrl}remote.php/dav/files/${configuration.user}/$ref"
    }

    data class Resource(
        val ref: String,
        val name: String,
        val isDirectory: Boolean,
        val isRoot: Boolean = false
    )
}