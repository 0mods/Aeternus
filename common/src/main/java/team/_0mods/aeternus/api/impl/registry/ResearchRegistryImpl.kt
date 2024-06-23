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

import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.util.debugIfEnabled
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation
import team._0mods.aeternus.api.util.mcemulate.createRL
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper
import kotlin.collections.set

@ApiStatus.Internal
class ResearchRegistryImpl(private val modId: String): ResearchRegistry {
    init {
        LOGGER.debugIfEnabled("Initialisation Research Registry for mod id '$modId'")
    }

    companion object {
        @JvmStatic
        private val researchMap: MutableMap<MCResourceLocation, Research> = linkedMapOf()
    }

    override val researches: List<Research>
        get() = researchMap.values.toList()

    override fun getById(id: MCResourceLocation): Research? {
        LOGGER.debugIfEnabled("Trying to get research with id '$id'")
        return researchMap[id]
    }

    override fun getId(research: Research): MCResourceLocation {
        LOGGER.debugIfEnabled("Getting research id for: $research")
        return researchMap.revert()[research] ?: throw NullPointerException("Research $research is not have an identifier. Why?")
    }

    override fun <T: Research> register(id: String, research: T): T {
        val resLocId = MCResourceLocation.createRL(modId, id)
        return this.register(resLocId, research)
    }

    override fun <T: Research> register(id: MCResourceLocation, research: T): T {
        LOGGER.debugIfEnabled("Registering research with id '$id'")

        if (researchMap.keys.stream().noneMatch { it == id })
            researchMap[id] = research
        else
            LOGGER.warn(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(id.rlNamespace),
                id
            )

        return research
    }

    override fun getByIdList(id: List<MCResourceLocation>): List<Research> = researchMap.fromMapToListByList(id)
}
