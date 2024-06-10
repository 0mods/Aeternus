/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("NetworkHelperImpl")

package team._0mods.aeternus.api.internal.forge

import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraftforge.network.Channel
import net.minecraftforge.network.ChannelBuilder
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.SimpleChannel
import team._0mods.aeternus.api.packets.*
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.api.util.c
import team._0mods.aeternus.api.util.nbt.NBTFormat
import team._0mods.aeternus.api.util.nbt.*
import team._0mods.aeternus.common.LOGGER

val aeternusChannel: SimpleChannel = ChannelBuilder.named("aeternus_packets".aRl)
    .networkProtocolVersion(4)
    .clientAcceptedVersions(Channel.VersionTest.exact(4))
    .serverAcceptedVersions(Channel.VersionTest.exact(4))
    .simpleChannel()

fun register() {
    registerPacket = {
        registerPacketF(it.c())
    }

    sendPacketToClient = { p, pack ->
        aeternusChannel.send(pack, PacketDistributor.PLAYER.with(p))
    }

    sendPacketToServer = {
        aeternusChannel.send(it, PacketDistributor.SERVER.noArg())
    }

    registerPackets()
}

fun <T : AeternusPacketInstance<T>> registerPacketF(type: Class<T>) {
    val annotation = type.getAnnotation(AeternusPacket::class.java)
    val location = CustomPacketPayload.Type<T>(type.name.lowercase().aRl)

    val codec: StreamCodec<FriendlyByteBuf, T> = CustomPacketPayload.codec(
        { packet, buffer ->
            val tag = NBTFormat.serializeNoInline(packet, type)
            if (tag is CompoundTag) buffer.writeNbt(tag)
            else buffer.writeNbt(CompoundTag().apply { put("data", tag) })
        },
        { buffer ->
            try {
                val tag = buffer.readNbt() ?: throw IllegalStateException("NBT is null")
                if (tag.contains("data")) NBTFormat.deserializeNoInline(tag.get("%%data")!!, type)
                else NBTFormat.deserializeNoInline(tag, type)
            } catch (e: Exception) {
                LOGGER.error("Can't decode ${type.name} packet!", e)
                throw e
            }
        }
    )

    when (annotation.distant) {
        AeternusPacket.Direction.CLIENT_PLAY -> {
            aeternusChannel
                .messageBuilder(type)
                .direction(PacketFlow.CLIENTBOUND)
                .encoder { packet, buffer -> codec.encode(buffer, packet) }
                .decoder(codec::decode)
                .consumerMainThread { t, u ->
                    t.handle(Minecraft.getInstance().player ?: throw IllegalStateException("Sender of packet is null!"))
                }
                .add()
        }

        AeternusPacket.Direction.SERVER_PLAY -> {
            aeternusChannel
                .messageBuilder(type)
                .direction(PacketFlow.SERVERBOUND)
                .encoder { packet, buffer -> codec.encode(buffer, packet) }
                .decoder(codec::decode)
                .consumerMainThread { t, u ->
                    t.handle(u.sender ?: throw IllegalStateException("Sender of packet is null!"))
                }
                .add()
        }

        AeternusPacket.Direction.ANY -> {
            aeternusChannel
                .messageBuilder(type)
                .direction(PacketFlow.CLIENTBOUND)
                .encoder { packet, buffer -> codec.encode(buffer, packet) }
                .decoder(codec::decode)
                .consumerMainThread { t, u ->
                    t.handle(Minecraft.getInstance().player ?: throw IllegalStateException("Sender of packet is null!"))
                }
                .add()
            aeternusChannel
                .messageBuilder(type)
                .direction(PacketFlow.SERVERBOUND)
                .encoder { packet, buffer -> codec.encode(buffer, packet) }
                .decoder(codec::decode)
                .consumerMainThread { t, u ->
                    t.handle(u.sender ?: throw IllegalStateException("Sender of packet is null!"))
                }
                .add()
        }
    }
}
