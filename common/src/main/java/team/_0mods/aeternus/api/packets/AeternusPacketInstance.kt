/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.packets

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerChunkCache
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import team._0mods.aeternus.api.util.aRl

interface AeternusPacketInstance<T: AeternusPacketInstance<T>>: CustomPacketPayload {
    fun handle(player: Player)

    fun send() {
        sendPacketToServer(this)
    }

    fun send(vararg player: ServerPlayer) {
        player.forEach {
            sendPacketToClient(it, this)
        }
    }

    override fun type() = CustomPacketPayload.Type<T>(javaClass.name.lowercase().aRl)
}

fun AeternusPacketInstance<*>.sendTrackingEntity(e: Entity) {
    val chunkCache = e.level().chunkSource
    if (chunkCache is ServerChunkCache) {
        chunkCache.broadcastAndSend(
            e,
            ClientboundCustomPayloadPacket(this)
        )
    } else {
        throw IllegalStateException("Cannot send clientbound payloads on the client")
    }
}

fun AeternusPacketInstance<*>.sendAllInDimension(level: Level) {
    val server = level.server ?: return
    server.playerList.broadcastAll(ClientboundCustomPayloadPacket(this), level.dimension())
}

lateinit var sendPacketToServer: (AeternusPacketInstance<*>) -> Unit
lateinit var sendPacketToClient: (ServerPlayer, AeternusPacketInstance<*>) -> Unit
lateinit var registerPacket: (Class<*>) -> Unit
lateinit var registerPackets: () -> Unit
