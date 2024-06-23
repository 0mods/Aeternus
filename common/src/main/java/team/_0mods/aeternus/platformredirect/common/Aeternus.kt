/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("Aeternus")

package team._0mods.aeternus.platformredirect.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team._0mods.aeternus.api.config.ConfigInstance
import team._0mods.aeternus.api.config.loadConfig
import team._0mods.aeternus.api.config.prefix
import team._0mods.aeternus.api.util.debugIfEnabled
import team._0mods.aeternus.platformredirect.common.init.config.AeternusClientConfig
import team._0mods.aeternus.platformredirect.common.init.config.AeternusCommonConfig
import team._0mods.aeternus.platformredirect.common.init.registry.AeternusRegsitry
import team._0mods.aeternus.reflect.AeternusAnnotationProcessor

const val ModId = "aeternus"
const val ModName = "Aeternus"

@JvmField val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const

lateinit var commonConfigInstance: ConfigInstance<AeternusCommonConfig> private set
lateinit var clientConfigInstance: ConfigInstance<AeternusClientConfig> private set

lateinit var commonConfig: AeternusCommonConfig private set
lateinit var clientConfig: AeternusClientConfig private set

fun commonInit() {
    commonConfigInstance = loadConfig(AeternusCommonConfig.defaultConfig, prefix("common"))
    clientConfigInstance = loadConfig(AeternusClientConfig.defaultConfig, prefix("client"))

    commonConfig = commonConfigInstance()
    clientConfig = clientConfigInstance()

    LOGGER.debugIfEnabled("DEBUG MODE IS ACTIVATED")

    AeternusRegsitry.init()
    AeternusAnnotationProcessor.start()
}

fun clientInit() {
}
