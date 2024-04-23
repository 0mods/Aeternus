/*
 * All Rights Received
 * Copyright (c) 2024.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service.core

import net.minecraft.world.entity.player.Player
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research

@ApiStatus.Internal
interface ResearchHelper {
    fun getResearches(player: Player): List<Research>

    fun hasResearch(player: Player, research: Research): Boolean

    fun addResearch(player: Player, vararg research: Research): Boolean

    fun canOpen(player: Player, research: Research): Boolean = false // TODO: Not work at current time
}
