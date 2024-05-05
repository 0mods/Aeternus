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
import team._0mods.aeternus.api.magic.research.trigger.ResearchTriggerInstance
import team._0mods.aeternus.api.magic.research.trigger.ResearchTriggerInstance.Companion.cast
import team._0mods.aeternus.api.magic.research.trigger.ResearchTriggerInstance.Companion.castNull
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.service.PlatformHelper

class ResearchTriggerRegistryImpl(private val modId: String): ResearchTriggerRegistry {
    private val triggerMap: MutableMap<ResourceLocation, ResearchTrigger> = linkedMapOf()
    private val triggerInstances: MutableMap<ResourceLocation, ResearchTriggerInstance<*, *>> = linkedMapOf()

    override val triggers: List<ResearchTrigger>
        get() = triggerMap.values.toList()

    override fun getResearchTriggerById(id: ResourceLocation): ResearchTrigger? =
        triggerMap[id]

    override fun register(id: String, research: ResearchTrigger) {
        val resLocId = "${this.modId}:$id".rl

        if (triggerMap.keys.stream().noneMatch { it == resLocId })
            triggerMap[resLocId] = research
        else
            LOGGER.atWarn().log(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(resLocId.namespace),
                resLocId
            )

    }

    override fun getByIdList(id: List<ResourceLocation>): List<ResearchTrigger> = triggerMap.fromMapToListByList(id)

    override fun <T : ResearchTrigger, V> register(id: String, research: ResearchTriggerInstance<T, V>) {
        val rlId = "${this.modId}:$id".rl
        if (triggerInstances.keys.stream().noneMatch { it == rlId })
            triggerInstances[rlId] = research
        else {
            LOGGER.warn(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(rlId.namespace),
                rlId
            )
        }
    }

    override fun <T: ResearchTrigger, V> getByIdGenericList(id: List<ResourceLocation>): List<ResearchTriggerInstance<T, V>> {
        val notyped = triggerInstances.fromMapToListByList(id)
        val typed: MutableList<ResearchTriggerInstance<T, V>> = mutableListOf()

        notyped.forEach { typed.add(it.cast()) }

        return typed
    }

    override fun <T : ResearchTrigger, V> getResearchTriggerInstanceById(id: ResourceLocation): ResearchTriggerInstance<T, V>? =
        triggerInstances[id].castNull()

}
