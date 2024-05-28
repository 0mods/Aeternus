/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper
import java.io.File

class ConfigInstance<T>(private val value: T, val fileName: String) {
    operator fun invoke() = value
}

inline fun <reified T> loadConfig(value: T, fileName: String): ConfigInstance<T> {
    LOGGER.debug("Loading config '$fileName'")
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    val file = PlatformHelper.gamePath().resolve("config/").toFile().resolve("$fileName.json")

    return if (file.exists()) ConfigInstance(decodeCfg(json, file), fileName)
    else {
        encodeCfg(json, value, file)
        ConfigInstance(value, fileName)
    }
}

inline fun <reified T> regenerateCfg(value: T, fileName: String): ConfigInstance<T> {
    LOGGER.debug("Regenerating config...")
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    val file = PlatformHelper.gamePath().resolve("config/").toFile().resolve("$fileName.json")

    encodeCfg(json, value, file)

    return ConfigInstance(value, fileName)
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> encodeCfg(json: Json, value: T, file: File) {
    try {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        json.encodeToStream(value, file.outputStream())
    } catch (e: FileSystemException) {
        LOGGER.error("Failed to write config to file '$file'", e)
    }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> decodeCfg(json: Json, file: File): T = try {
    json.decodeFromStream(file.inputStream())
} catch (e: FileSystemException) {
    LOGGER.error("Failed to read config from file '$file'", e)
    throw e
}

internal fun prefix(text: String) = "aeternus/$text"