package eu.heha.ncfilerenamer.model

import com.github.sardine.SardineFactory
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LoginRepository {
    suspend fun login(user: String, password: String, baseUrl: String): Unit = withContext(IO) {
        val sardine = SardineFactory.begin(user, password)
        sardine.list("$baseUrl/remote.php/dav/files/$user/Photos")
    }
}