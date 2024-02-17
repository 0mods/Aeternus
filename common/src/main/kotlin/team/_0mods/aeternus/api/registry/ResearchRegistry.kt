/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
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
    val researches: List<Research>

    fun getResearchById(id: ResourceLocation): Research?

    fun getIdByResearch(research: Research): ResourceLocation?

    @ApiStatus.ScheduledForRemoval
    @Deprecated("Deprecated.",
        ReplaceWith("register(ResourceLocation, Research)"),
        level = DeprecationLevel.ERROR
    )
    fun register(research: Research) = register(research.name.toString(), research)

    fun register(id: String, research: Research)

    fun registerAll(vararg researches: Pair<String, Research>) {
        for (researchPair in researches) {
            val id = researchPair.first
            val research = researchPair.second

            register(id, research)
        }
    }

    fun getResearchListByIdList(id: List<ResourceLocation>): List<Research>
}
