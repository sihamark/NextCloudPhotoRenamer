package eu.heha.ncfilerenamer.model

import com.github.sardine.DavResource
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
        return "/remote.php/dav/files/${configuration.user}/"
    }

    suspend fun loadFileContent(ref: String): ResourceContent = withContext(IO) {
        val uri = "${configuration.baseUrl}$ref"
        Napier.e { "Loading from $uri" }

        val resources = sardine().list(uri)
        val refResource = resources.first { it.href.toString() == ref }
        val content = (resources - refResource).map { davResource -> davResource.toResource() }
        ResourceContent(
            resource = refResource.toResource(),
            content = content
        )
    }

    private suspend fun DavResource.toResource(): Resource {
        val ref = href.toString()
        return Resource(
            ref = ref,
            name = URLDecoder.decode(name, Charsets.UTF_8),
            isDirectory = isDirectory,
            isRoot = ref == rootRef()
        )
    }

    data class ResourceContent(
        val resource: Resource,
        val content: List<Resource>
    )

    data class Resource(
        val ref: String,
        val name: String,
        val isDirectory: Boolean,
        val isRoot: Boolean = false
    )
}