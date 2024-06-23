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

import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation
import team._0mods.aeternus.api.util.mcemulate.createRL
import team._0mods.aeternus.api.util.noneMatchKey
import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper

class ResearchTriggerRegistryImpl(private val modId: String): ResearchTriggerRegistry {
    companion object {
        @JvmStatic
        private val triggerMap: MutableMap<MCResourceLocation, ResearchTrigger> = linkedMapOf()
    }

    override val triggers: List<ResearchTrigger>
        get() = triggerMap.values.toList()

    override fun getById(id: MCResourceLocation): ResearchTrigger? =
        triggerMap[id]

    override fun register(id: String, research: ResearchTrigger) {
        val resLocId = MCResourceLocation.createRL(modId, id)

        if (triggerMap.noneMatchKey(resLocId)) triggerMap[resLocId] = research
        else warn(resLocId)
    }

    override fun getByIdList(id: List<MCResourceLocation>): List<ResearchTrigger> = triggerMap.fromMapToListByList(id)

    private fun warn(id: MCResourceLocation) = LOGGER.warn(
        "Oh... Mod: {} trying to register a research trigger with id {}, because research with this id is already registered! Skipping...",
        PlatformHelper.getModNameByModId(id.rlNamespace),
        id
    )
}
