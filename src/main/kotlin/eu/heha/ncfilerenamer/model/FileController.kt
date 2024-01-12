package eu.heha.ncfilerenamer.model

import com.github.sardine.Sardine
import com.github.sardine.SardineFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URLDecoder

class FileController {

    private lateinit var configuration: ConfigurationRepository.Configuration
    private lateinit var _sardine: Sardine

    private suspend fun sardine(): Sardine {
        if (!::_sardine.isInitialized) {
            withContext(IO) {
                val config = ConfigurationRepository.load()
                    ?: error("configuration not present")
                configuration = config
                _sardine = SardineFactory.begin(config.user, config.password)
            }
        }
        return _sardine
    }

    suspend fun rootRef(): String {
        sardine()
        return "remote.php/dav/files/${configuration.user}/"
    }

    suspend fun loadFileContent(ref: String): List<Resource> = withContext(IO) {
        val uri = "${configuration.baseUrl}$ref"
        Napier.e { "Loading from $uri" }
        sardine().list(uri)
            .filter {
                it.href.toString() != ref
            }
            .map { davResource ->
                Resource(
                    ref = davResource.href.toString(),
                    name = URLDecoder.decode(davResource.name, Charsets.UTF_8),
                    isDirectory = davResource.isDirectory,
                    isRoot = ref == rootRef()
                )
            }
    }

    data class Resource(
        val ref: String,
        val name: String,
        val isDirectory: Boolean,
        val isRoot: Boolean = false
    )
}