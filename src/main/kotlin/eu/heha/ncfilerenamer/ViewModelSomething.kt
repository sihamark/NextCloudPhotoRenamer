package eu.heha.ncfilerenamer

import com.github.sardine.SardineFactory
import eu.heha.ncfilerenamer.model.ConfigurationRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ViewModelSomething : CoroutineScope by MainScope() {
    fun something() {
        launch(Dispatchers.IO) {
            fundasda()
        }
    }

    fun close() {
        cancel()
    }

    companion object {
        suspend fun fundasda() {
            try {
                val config = ConfigurationRepository.load() ?: return
                val url = "${config.baseUrl}/remote.php/dav/files/${config.user}/Photos"
                Napier.e { "trying to connect to $url" }
                val sardine = SardineFactory.begin(config.user, config.password)
                val resources =
                    sardine.list(url)
                Napier.e { "found ${resources.size} resources" }
                val resource = resources.first { !it.isDirectory }
                Napier.e { "found resource: ${resource.href}" }
                val pathSegments = resource.href.path.split("/")
                val fileName = pathSegments.last()
                if (fileName.startsWith("PXL_")) {
                    val newFileName = fileName.replace("PXL_", "")
                    val newHref = (pathSegments.dropLast(1) + newFileName).joinToString("/")
                    Napier.e { "renaming $fileName to $newFileName, new href: $newHref" }
                    Napier.e { "      resource: $newHref" }
//                    sardine.move(
//                        config.baseUrl + resource.href,
//                        config.baseUrl + newHref
//                    )
                }

                //                sardine.move(config.baseUrl + resource.href)
            } catch (e: Exception) {
                Napier.e("something went wrong", e)
            }
        }
    }
}