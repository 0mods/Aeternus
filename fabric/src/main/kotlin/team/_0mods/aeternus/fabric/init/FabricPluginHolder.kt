/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.fabric.init

import net.fabricmc.loader.api.FabricLoader
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.plugin.PluginHolder
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.impl.registry.ResearchRegistryImpl
import java.util.stream.Collectors

class FabricPluginHolder: PluginHolder {
    override fun loadPlugins() {
        val listOfPlugins = FabricLoader.getInstance()
            .getEntrypointContainers("${ModId}_plugin", AeternusPlugin::class.java)
            .stream()
            .map { it.entrypoint }
            .collect(Collectors.toList())

        listOfPlugins.forEach {
            it.registerResearch(ResearchRegistryImpl(it.modId))
            it.registerResearchTriggers(team._0mods.aeternus.common.impl.registry.ResearchTriggerRegistryImpl(it.modId))
        }
    }
}
