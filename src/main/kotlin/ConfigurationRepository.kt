import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

object ConfigurationRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun load(): Configuration = withContext(Dispatchers.IO) {
        File("config.json").also { Napier.e("path: $it") }.inputStream().use {
            Json.decodeFromStream<Configuration>(it)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun save(configuration: Configuration) = withContext(Dispatchers.IO) {
        File("config.json").also { Napier.e("path: $it") }.outputStream().use {
            Json.encodeToStream(configuration, it)
        }
    }

    @Serializable
    data class Configuration(
        val userName: String,
        val password: String,
        val baseUrl: String
    )
}