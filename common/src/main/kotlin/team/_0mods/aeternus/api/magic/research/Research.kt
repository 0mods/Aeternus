/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research

import net.minecraft.resources.ResourceLocation

interface Research {
    /**
     * Research name.
     *
     * Returns [ResourceLocation]
     */
    val name: ResourceLocation

    /**
     * List of requirement researches for current research.
     * It could be empty.
     * If previous researches is not opened, this research can't be opened
     *
     * Returns [List] of [Research]es
     */
    val depends: List<Research>

    /**
     * Count of consuming Etherium size.
     *
     * Returns count of needed etherium
     */
    val etheriumNeedValue: Double
        get() = 0.0

    /**
     * Triggers, after which it opens current research
     *
     * Returns [List] of [ResearchTrigger]
     */
    val triggers: List<ResearchTrigger>

    val bookMetadata: ResearchBookMetadata

    /**
     * Haven't a javadoc. Sorry! I'm Lazy
     */
    fun addRequirementResearch(vararg research: Research) {}

    fun addTriggers(vararg trigger: ResearchTrigger) {}
}
