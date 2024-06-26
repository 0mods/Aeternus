/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.research

import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchSettings
import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger

internal class ResearchSettingsImpl(
    private val triggerList: MutableList<ResearchTrigger>,
    private val dependencies: MutableList<Research>
): ResearchSettings {
    override val depends: List<Research>
        get() = dependencies

    override val triggers: List<ResearchTrigger>
        get() = triggerList

    override fun addRequirementResearch(vararg research: Research) {
        triggerList
    }

    override fun addTriggers(vararg trigger: ResearchTrigger) {
        triggerList.addAll(trigger)
    }
}
