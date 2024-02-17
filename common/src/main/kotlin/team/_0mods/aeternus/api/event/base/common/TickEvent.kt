/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.common

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventFactory
import net.minecraft.world.entity.player.Player as MCPlayer


fun interface TickEvent<T> {
    companion object {
        @JvmField val SERVER_PRE: Event<Server> = EventFactory.createNoResult()

        @JvmField val SERVER_POST: Event<Server> = EventFactory.createNoResult()

        @JvmField val SERVER_LEVEL_PRE: Event<ServerLevelTick> = EventFactory.createNoResult()

        @JvmField val SERVER_LEVEL_POST: Event<ServerLevelTick> = EventFactory.createNoResult()

        @JvmField val PLAYER_PRE: Event<Player> = EventFactory.createNoResult()

        @JvmField val PLAYER_POST: Event<Player> = EventFactory.createNoResult()
    }

    fun tick(instance: T)

    fun interface Server: TickEvent<MinecraftServer>

    fun interface LevelTick<T: Level>: TickEvent<T>

    fun interface ServerLevelTick: LevelTick<ServerLevel>

    fun interface Player: TickEvent<MCPlayer>
}