/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forgelike.forge

import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.magic.PlayerEtherium
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.capability.ANCapabilities

object CapabilityLikeImpl {
    @JvmStatic
    fun getEtherium(player: Player): PlayerEtherium {
        val cap = player.getCapability(ANCapabilities.playerEtherium, player) ?: throw NullPointerException()
        return cap
    }

    @JvmStatic
    fun getResearch(player: Player): PlayerResearch {
        val cap = player.getCapability(ANCapabilities.playerResearch, player) ?: throw NullPointerException()
        return cap
    }
}