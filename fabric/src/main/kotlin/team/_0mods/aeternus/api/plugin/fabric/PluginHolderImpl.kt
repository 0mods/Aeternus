/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.plugin.fabric

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.entrypoint.EntrypointContainer
import team._0mods.aeternus.api.plugin.AeternusPlugin
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.impl.registry.ResearchRegistryImpl
import team._0mods.aeternus.common.impl.registry.ResearchTriggerRegistryImpl
import java.util.stream.Collectors

object PluginHolderImpl {
    @JvmStatic
    fun loadPlugins() {
        FabricLoader.getInstance().getEntrypointContainers("${ModId}_plugin", AeternusPlugin::class.java).stream()
            .map(::entrypoint).collect(Collectors.toList()).forEach(::regAll)
    }

    private fun regAll(plugin: AeternusPlugin) {
        val mid = plugin.modId
        plugin.registerResearch(ResearchRegistryImpl(mid))
        plugin.registerResearchTriggers(ResearchTriggerRegistryImpl(mid))
    }

    private fun <T> entrypoint(container: EntrypointContainer<T>) = container.entrypoint
}