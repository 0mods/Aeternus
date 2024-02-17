/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.player.LocalPlayer
import team._0mods.aeternus.api.event.core.EventFactory

interface ClientPlayerEvent {
    companion object {
        @JvmField val PLAYER_JOIN = EventFactory.createNoResult<PlayerJoin>()
        @JvmField val PLAYER_LEAVE = EventFactory.createNoResult<PlayerLeave>()
        @JvmField val PLAYER_CLONE = EventFactory.createNoResult<PlayerClone>()
    }

    fun interface PlayerJoin {
        fun onJoin(player: LocalPlayer)
    }

    fun interface PlayerLeave {
        fun onLeave(player: LocalPlayer?)
    }

    fun interface PlayerClone {
        fun onClone(old: LocalPlayer, new: LocalPlayer)
    }
}
