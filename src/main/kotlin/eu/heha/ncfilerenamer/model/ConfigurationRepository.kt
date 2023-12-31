package eu.heha.ncfilerenamer.model

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
    suspend fun load(): Configuration? = withContext(Dispatchers.IO) {
        try {
            File("data", "config.json").inputStream().use { inputStream ->
                Json.decodeFromStream<Configuration>(inputStream)
            }
        } catch (e: Exception) {
            Napier.e("Error loading configuration", e)
            null
        }
    }

    @Suppress("unused")
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun save(
        user: String, password: String, baseUrl: String
    ) = withContext(Dispatchers.IO) {
        val directory = File("data")
        directory.mkdirs()
        directory.resolve("config.json").outputStream().use { outputStream ->
            Json.encodeToStream(Configuration(user, password, baseUrl), outputStream)
        }
    }

    @Serializable
    data class Configuration(
        val user: String,
        val password: String,
        val baseUrl: String
    )
}