/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.init

import com.mojang.logging.LogUtils

import net.minecraftforge.fml.ModList
import team._0mods.aeternus.api.*
import team._0mods.aeternus.common.impl.registry.*

object PluginHolder {
    private val logger = LogUtils.getLogger()
    private val list: MutableList<IAeternusPlugin> = mutableListOf()

    fun loadPlugins() {
        ModList.get().allScanData.forEach { data ->
            data.annotations.forEach { annot ->
                if (annot.annotationType.className.equals(AeternusPlugin::class.java)) {
                    try {
                        val clazz = Class.forName(annot.memberName)
                        if (IAeternusPlugin::class.java.isAssignableFrom(clazz)) {
                            val plugin: IAeternusPlugin =
                                clazz.getDeclaredConstructor().newInstance() as IAeternusPlugin
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
            it.registerResearchTriggers(ResearchTriggerRegistryImpl(it.modId))
        }
    }
}
