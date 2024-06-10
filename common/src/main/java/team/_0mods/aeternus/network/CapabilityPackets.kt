/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.network

import net.minecraft.nbt.Tag
import kotlinx.serialization.Serializable
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.capability.ICapabilityDispatcher
import team._0mods.aeternus.api.packets.AeternusPacket
import team._0mods.aeternus.api.packets.AeternusPacketInstance
import team._0mods.aeternus.api.packets.sendAllInDimension
import team._0mods.aeternus.api.util.nbt.ForTag
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.common.LOGGER

@AeternusPacket(AeternusPacket.Direction.CLIENT_PLAY)
@Serializable
class CSyncEntityCapabilityPacket(
    private val entityId: Int,
    val capability: String,
    val value: @Serializable(ForTag::class) Tag,
) : AeternusPacketInstance<CSyncEntityCapabilityPacket> {
    override fun handle(player: Player) {
        val entity = player.level().getEntity(entityId)
            ?: throw IllegalStateException("Entity with id $entityId not found: $this".apply(LOGGER::warn))
        val cap = (entity as ICapabilityDispatcher).capabilities.first { it.javaClass.name == capability }

        if ((value as? CompoundTag)?.isEmpty == false) {
            cap.deserializeNBT(value)
        }
    }
}

@AeternusPacket(AeternusPacket.Direction.SERVER_PLAY)
@Serializable
class SSyncEntityCapabilityPacket(
    private val entityId: Int,
    val capability: String,
    val value: @Serializable(ForTag::class) Tag,
) : AeternusPacketInstance<SSyncEntityCapabilityPacket> {
    override fun handle(player: Player) {
        val entity = player.level().getEntity(entityId)
            ?: throw IllegalStateException("Entity with id $entityId not found: $this".apply(LOGGER::warn))
        val cap = (entity as ICapabilityDispatcher).capabilities.first { it.javaClass.name == capability }

        if (cap.consumeOnServer) {
            cap.deserializeNBT(value)
            CSyncEntityCapabilityPacket(
                entityId, capability, value
            ).send(*player.level().server!!.playerList.players.toTypedArray())
        }
    }

}

@AeternusPacket(AeternusPacket.Direction.CLIENT_PLAY)
@Serializable
class CSyncLevelCapabilityPacket(
    val capability: String,
    val value: @Serializable(ForTag::class) Tag,
) : AeternusPacketInstance<CSyncLevelCapabilityPacket> {
    override fun handle(player: Player) {
        val level = player.level() as ICapabilityDispatcher
        val cap = level.capabilities.first { it.javaClass.name == capability }

        if ((value as? CompoundTag)?.isEmpty == false) {
            cap.deserializeNBT(value)
        }
    }

}

@AeternusPacket(AeternusPacket.Direction.SERVER_PLAY)
@Serializable
class SSyncLevelCapabilityPacket(
    val level: String,
    val capability: String,
    val value: @Serializable(ForTag::class) Tag,
) : AeternusPacketInstance<SSyncLevelCapabilityPacket> {
    override fun handle(player: Player) {
        val server = player.server ?: throw IllegalStateException("Server not found".apply(LOGGER::warn))
        val levelKey = server.levelKeys().find { it.location() == level.rl }
            ?: throw IllegalStateException("Unknown level: $level".apply(LOGGER::warn))
        val level = server.getLevel(levelKey)
            ?: throw IllegalStateException("Level not found: $level".apply(LOGGER::warn))
        val cap = (level as ICapabilityDispatcher).capabilities.first { it.javaClass.name == capability }

        if (cap.consumeOnServer) {
            cap.deserializeNBT(value)
            CSyncLevelCapabilityPacket(
                capability,
                value
            ).sendAllInDimension(level)
        }
    }
}
