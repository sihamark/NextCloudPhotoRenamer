import com.github.sardine.SardineFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ViewModelSomething : CoroutineScope by MainScope() {
    fun something() {
        launch(Dispatchers.IO) {
            try {
                val config = ConfigurationRepository.load()
                Napier.e { "trying to connect" }
                val sardine = SardineFactory.begin(config.userName, config.password)
                val resources =
                    sardine.list("${config.baseUrl}/remote.php/dav/files/${config.userName}/Photos")
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
                    sardine.move(
                        config.baseUrl + resource.href,
                        config.baseUrl + newHref
                    )
                }

//                sardine.move(config.baseUrl + resource.href)
            } catch (e: Exception) {
                Napier.e("something went wrong", e)
            }
        }
    }

    fun close() {
        cancel()
    }
}