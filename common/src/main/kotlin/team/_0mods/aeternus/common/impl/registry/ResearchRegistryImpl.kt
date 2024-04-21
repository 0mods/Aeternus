/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.impl.registry

import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.ServiceProvider

@ApiStatus.Internal
class ResearchRegistryImpl(private val modId: String): ResearchRegistry {
    private val researchMap: MutableMap<ResourceLocation, Research> = linkedMapOf()

    override val researches: List<Research>
        get() = researchMap.values.toList()

    override fun getResearchById(id: ResourceLocation): Research = researchMap[id] ?: throw NullPointerException("Research with id \"$id\" is not found! Make sure that a research with that id is actually there.")

    override fun getIdByResearch(research: Research): ResourceLocation =
        this.getIdByResearch(0, research)

    // is safe from duplicates, as it checks for repeated researches
    override fun getIdByResearch(id: Int, research: Research): ResourceLocation {
        val researchesMap = researchMap.revert()
        val availableResearches = mutableListOf<Research>()

        researchesMap.forEach {
            val res = it.key
            availableResearches.add(res)
        }

        if (availableResearches.size > 1) {
            LOGGER.error("Founded duplicated researches:")
            availableResearches.forEach {
                LOGGER.error("$it")
            }
            LOGGER.warn("Using an alternative variant with id: $id. Registry name: ${researchMap.revert()[availableResearches[id]]}")
            return researchMap.revert()[availableResearches[id]] ?: throw NullPointerException("Research $research\$$id is not have an identifier. Why?")
        }

        availableResearches.clear()

        return researchMap.revert()[research] ?: throw NullPointerException("Research $research is not have an identifier. Why?")
    }

    override fun register(id: String, research: Research) {
        val resLocId = "${this.modId}:$id".rl
        if (researchMap.keys.stream().noneMatch { it == resLocId })
            researchMap[resLocId] = research
        else
            LOGGER.warn(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                ServiceProvider.platform.getModNameByModId(resLocId.namespace),
                resLocId
            )
    }

    override fun getResearchListByIdList(id: List<ResourceLocation>): List<Research> = researchMap.fromMapToListByList(id)
}
