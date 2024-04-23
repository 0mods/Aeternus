/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service

import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.core.EtheriumHelper
import team._0mods.aeternus.service.core.PlatformHelper
import team._0mods.aeternus.service.core.ResearchHelper
import java.util.*
import kotlin.reflect.KClass

object ServiceProvider {
    val researchHelper: ResearchHelper = initPlatformed(ResearchHelper::class)
    val platform: PlatformHelper = initPlatformed(PlatformHelper::class)
    val etheriumHelper: EtheriumHelper = initPlatformed(EtheriumHelper::class)

    fun <T> initPlatformed(clazz: KClass<T>): T where T: Any {
        val loaded: T = ServiceLoader.load(clazz.java).findFirst().orElseThrow { NullPointerException("Failed to load Service for ${clazz.simpleName}") }
        LOGGER.debug("Loading service {} for {}...", loaded, clazz)
        return loaded
    }
}
