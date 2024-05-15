/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.plugin.forge

import com.mojang.logging.LogUtils
import net.minecraftforge.fml.ModList
import org.slf4j.LoggerFactory
import team._0mods.aeternus.api.impl.registry.*
import team._0mods.aeternus.api.plugin.*

object PluginHolderImpl {
    private val logger = LoggerFactory.getLogger(PluginHolder::class.java)
    private val pluginList: MutableList<AeternusPlugin> = mutableListOf()

    @JvmStatic
    fun loadPlugins() {
        ModList.get().allScanData.forEach { data ->
            data.annotations.forEach { annot ->
                if (annot.annotationType.className.equals(AeternusPluginInit::class.java)) {
                    try {
                        val clazz = Class.forName(annot.memberName)
                        if (AeternusPlugin::class.java.isAssignableFrom(clazz)) {
                            val plugin: AeternusPlugin =
                                clazz.getDeclaredConstructor().newInstance() as AeternusPlugin
                            pluginList.add(plugin)
                            logger.info("Plugin {} has been registered!", annot.memberName)
                        }
                    } catch (e: Exception) {
                        logger.error(LogUtils.FATAL_MARKER, "Error during loading plugin: {}", annot.memberName, e)
                    }
                }
            }
        }

        pluginList.forEach {
            it.registerResearch(ResearchRegistryImpl(it.modId))
            it.registerResearchTriggers(ResearchTriggerRegistryImpl(it.modId))
        }
    }
}