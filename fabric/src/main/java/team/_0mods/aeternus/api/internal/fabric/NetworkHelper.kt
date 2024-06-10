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

package team._0mods.aeternus.api.internal.fabric

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import team._0mods.aeternus.api.packets.*
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.api.util.c
import team._0mods.aeternus.api.util.nbt.NBTFormat
import team._0mods.aeternus.api.util.nbt.deserializeNoInline
import team._0mods.aeternus.api.util.nbt.serializeNoInline
import team._0mods.aeternus.common.LOGGER

fun register() {
    registerPacket = {
        registerPacketF(it.c())
    }

    sendPacketToClient = { p, packet ->
        ServerPlayNetworking.send(p, packet)
    }

    sendPacketToServer = {
        ClientPlayNetworking.send(it)
    }

    registerPackets()
}

fun <T : AeternusPacketInstance<T>> registerPacketF(type: Class<T>) {
    val annotation = type.getAnnotation(AeternusPacket::class.java)
    val location = CustomPacketPayload.Type<T>(type.name.lowercase().aRl)

    val codec: StreamCodec<RegistryFriendlyByteBuf, T> = CustomPacketPayload.codec(
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
            PayloadTypeRegistry.playS2C()
                .register(location, codec)
            ClientPlayNetworking.registerGlobalReceiver(location) { payload: T, context: ClientPlayNetworking.Context ->
                payload.handle(context.player())
            }
        }

        AeternusPacket.Direction.SERVER_PLAY -> {
            PayloadTypeRegistry.playC2S()
                .register(location, codec)
            ServerPlayNetworking.registerGlobalReceiver(location) { payload: T, context: ServerPlayNetworking.Context ->
                payload.handle(context.player())
            }
        }

        AeternusPacket.Direction.ANY -> {
            PayloadTypeRegistry.playC2S()
                .register(location, codec)
            PayloadTypeRegistry.playS2C()
                .register(location, codec)
            ServerPlayNetworking.registerGlobalReceiver(location) { payload: T, context: ServerPlayNetworking.Context ->
                payload.handle(context.player())
            }
            ClientPlayNetworking.registerGlobalReceiver(location) { payload: T, context: ClientPlayNetworking.Context ->
                payload.handle(context.player())
            }
        }
    }
}
