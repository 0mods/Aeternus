/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service.fabric

import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.player.PlayerResearch

object ResearchHelperImpl {
    private val Player.research: PlayerResearch
        get() = this as PlayerResearch

    @JvmStatic
    fun getResearches(player: Player): List<Research> = player.research.researches

    @JvmStatic
    fun hasResearch(player: Player, research: Research): Boolean = player.research.hasResearch(research)

    @JvmStatic
    fun addResearch(player: Player, vararg researches: Research): Boolean = player.research.addResearch(*researches)
}
