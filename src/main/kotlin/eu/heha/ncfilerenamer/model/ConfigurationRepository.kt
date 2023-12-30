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
    suspend fun load(): Configuration = withContext(Dispatchers.IO) {
        File("data","config.json").also { Napier.e("path: $it") }.inputStream().use {
            Json.decodeFromStream<Configuration>(it)
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
        val userName: String,
        val password: String,
        val baseUrl: String
    )
}