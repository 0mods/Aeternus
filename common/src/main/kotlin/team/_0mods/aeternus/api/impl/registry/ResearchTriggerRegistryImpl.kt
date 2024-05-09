/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper

class ResearchTriggerRegistryImpl(private val modId: String): ResearchTriggerRegistry {
    private val triggerMap: MutableMap<ResourceLocation, ResearchTrigger> = linkedMapOf()

    override val triggers: List<ResearchTrigger>
        get() = triggerMap.values.toList()

    override fun getResearchTriggerById(id: ResourceLocation): ResearchTrigger? =
        triggerMap[id]

    override fun register(id: String, research: ResearchTrigger) {
        val resLocId = "${this.modId}:$id".rl

        if (triggerMap.keys.stream().noneMatch { it == resLocId })
            triggerMap[resLocId] = research
        else warn(resLocId)
    }

    override fun getByIdList(id: List<ResourceLocation>): List<ResearchTrigger> = triggerMap.fromMapToListByList(id)

    private fun <T: ResearchTrigger> tryClassUse(clazz: String): Boolean {
        return try {
            Class.forName(clazz)
            true
        } catch (ignored: ClassNotFoundException) { false }
    }

    private fun warn(id: ResourceLocation) = LOGGER.warn(
        "Oh... Mod: {} trying to register a research trigger with id {}, because research with this id is already registered! Skipping...",
        PlatformHelper.getModNameByModId(id.namespace),
        id
    )
}
