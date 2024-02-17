/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.init

import com.mojang.logging.LogUtils
import net.neoforged.fml.ModList
import team._0mods.aeternus.api.AeternusPluginInit
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.common.impl.registry.ResearchRegistryImpl

object PluginHolder {
    private val logger = LogUtils.getLogger()
    private val list: MutableList<AeternusPlugin> = mutableListOf()

    fun loadPlugins() {
        ModList.get().allScanData.forEach { data ->
            data.annotations.forEach { annot ->
                if (annot.annotationType.className.equals(AeternusPluginInit::class.java)) {
                    try {
                        val clazz = Class.forName(annot.memberName)
                        if (AeternusPlugin::class.java.isAssignableFrom(clazz)) {
                            val plugin: AeternusPlugin = clazz.getDeclaredConstructor().newInstance() as AeternusPlugin
                            list.add(plugin)
                            logger.info("Plugin {} has been registered!", annot.memberName)
                        }
                    } catch (e: Exception) {
                        logger.error(LogUtils.FATAL_MARKER, "Error during loading plugin: {}", annot.memberName, e)
                    }
                }
            }
        }

        list.forEach {
            it.registerResearch(ResearchRegistryImpl(it.modId))
            it.registerResearchTriggers(team._0mods.aeternus.common.impl.registry.ResearchTriggerRegistryImpl(it.modId))
        }
    }
}
