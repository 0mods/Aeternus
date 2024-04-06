/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.init.capability

import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.common.LOGGER

class PlayerResearchCapability: PlayerResearch {
    private val researchList: MutableList<Research> = mutableListOf()

    override val researches: List<Research>
        get() = researchList.toList() // Copy from list

    override fun addResearch(vararg research: Research) {
        for (researchImpl in research) {
            if (this.researchList.stream().noneMatch { it.name == researchImpl.name }) researchList.add(researchImpl)
            else {
                LOGGER.atWarn().log(
                    "Player already equals {} research. Why you will try to add it again?",
                    researchImpl.name
                )
                continue
            }
        }
    }
}
