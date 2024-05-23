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
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper

@ApiStatus.Internal
class ResearchRegistryImpl(private val modId: String): ResearchRegistry {
    companion object {
        @JvmStatic
        private val researchMap: MutableMap<ResourceLocation, Research> = linkedMapOf()
    }

    override val researches: List<Research>
        get() = researchMap.values.toList()

    override fun getById(id: ResourceLocation): Research = researchMap[id] ?: throw NullPointerException("Research with id \"$id\" is not found! Make sure that a research with that id is actually there.")

    override fun getId(research: Research): ResourceLocation =
        this.getId(0, research)

    // is safe from duplicates, as it checks for repeated researches
    override fun getId(id: Int, research: Research): ResourceLocation = researchMap.revert()[research] ?: throw NullPointerException("Research $research is not have an identifier. Why?")

    override fun <T: Research> register(id: String, research: T): T {
        val resLocId = "${this.modId}:$id".rl
        return this.register(resLocId, research)
    }

    override fun <T: Research> register(id: ResourceLocation, research: T): T {
        if (researchMap.keys.stream().noneMatch { it == id })
            researchMap[id] = research
        else
            LOGGER.warn(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(id.namespace),
                id
            )

        return research
    }

    override fun getByIdList(id: List<ResourceLocation>): List<Research> = researchMap.fromMapToListByList(id)
}
