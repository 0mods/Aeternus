/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.impl.research

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchTrigger

abstract class SimpleResearch(private val id: ResourceLocation): Research {
    private val required: MutableList<Research> = mutableListOf()
    private val triggerList: MutableList<ResearchTrigger> = mutableListOf()

    override val name: ResourceLocation
        get() = this.id

    override val depends: List<Research>
        get() = this.required

    override val triggers: List<ResearchTrigger>
        get() = this.triggerList

    override fun addRequirementResearch(vararg research: Research) {
        required.addAll(research)
    }

    override fun addTriggers(vararg trigger: ResearchTrigger) {
        triggerList.addAll(trigger)
    }
}
