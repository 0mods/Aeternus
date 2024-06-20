/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research

import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger

interface ResearchSettings {
    companion object
    /**
     * List of requirement researches for current research.
     * It could be empty.
     * If previous researches are not opened, this research can't be opened
     *
     * Returns [List] of [Research]es
     */
    val depends: List<Research>

    /**
     * Triggers, after which it opens current research
     *
     * Returns [List] of [ResearchTrigger]
     */
    val triggers: List<ResearchTrigger>

    /**
     * Adds to [depends] researches without which research cannot be opened
     */
    fun addRequirementResearch(vararg research: Research)

    /**
     * Adds triggers to [triggers] after which the research will open
     */
    fun addTriggers(vararg trigger: ResearchTrigger)
}
