/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research

interface ResearchRegistry {
    /**
     * List of registered researches.
     * Is not mutable!
     *
     * Returns [List] of [Research]es
     */
    val researches: List<Research>

    fun getResearchById(id: ResourceLocation): Research

    fun getIdByResearch(research: Research): ResourceLocation

    fun getIdByResearch(id: Int, research: Research): ResourceLocation

    @ApiStatus.ScheduledForRemoval
    @Deprecated("Deprecated.",
        level = DeprecationLevel.ERROR,
        replaceWith = ReplaceWith("register(research.name.toString(), research)")
    )
    fun register(research: Research): Research = register(research.name, research)

    fun <T: Research> register(id: String, research: T): T

    @ApiStatus.Experimental
    fun <T: Research> register(id: ResourceLocation, research: T): T

    fun registerAll(vararg researches: Pair<String, Research>) {
        for (researchPair in researches) {
            val id = researchPair.first
            val research = researchPair.second

            register(id, research)
        }
    }

    fun getResearchListByIdList(id: List<ResourceLocation>): List<Research>
}
