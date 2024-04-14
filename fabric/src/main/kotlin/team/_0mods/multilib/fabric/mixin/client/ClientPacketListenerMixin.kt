package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.multiplayer.CommonListenerCookie
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundLoginPacket
import net.minecraft.network.protocol.game.ClientboundRespawnPacket
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket
import net.minecraft.world.item.crafting.RecipeManager
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.client.ClientChatEvent
import team._0mods.multilib.event.base.client.ClientPlayerEvent
import team._0mods.multilib.event.base.client.ClientRecipeUpdateEvent
import team._0mods.multilib.event.result.EventResult

@Mixin(ClientPacketListener::class)
abstract class ClientPacketListenerMixin(minecraft: Minecraft,
                                         connection: Connection, commonListenerCookie: CommonListenerCookie
) : ClientCommonPacketListenerImpl(minecraft, connection, commonListenerCookie) {
    @Shadow
    @Final
    private lateinit var recipeManager: RecipeManager

    @Unique
    private var tmpPlayer: LocalPlayer? = null

    @Inject(
        method = ["handleLogin"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Options;setServerRenderDistance(I)V",
            shift = At.Shift.AFTER
        )]
    )
    private fun handleLogin(packet: ClientboundLoginPacket, ci: CallbackInfo) {
        ClientPlayerEvent.PLAYER_JOIN.event.onJoin(minecraft.player!!)
    }

    @Inject(method = ["handleRespawn"], at = [At("HEAD")])
    private fun handleRespawnPre(packet: ClientboundRespawnPacket, ci: CallbackInfo) {
        this.tmpPlayer = minecraft.player
    }

    @Inject(
        method = ["handleRespawn"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientLevel;addEntity(Lnet/minecraft/world/entity/Entity;)V"
        )]
    )
    private fun handleRespawn(packet: ClientboundRespawnPacket, ci: CallbackInfo) {
        ClientPlayerEvent.PLAYER_CLONE.event.onClone(tmpPlayer!!, minecraft.player!!)
        this.tmpPlayer = null
    }

    @Inject(method = ["handleUpdateRecipes"], at = [At("RETURN")])
    private fun handleUpdateRecipes(clientboundUpdateRecipesPacket: ClientboundUpdateRecipesPacket, ci: CallbackInfo) {
        ClientRecipeUpdateEvent.EVENT.event.update(recipeManager)
    }

    @Inject(method = ["sendChat(Ljava/lang/String;)V"], at = [At(value = "HEAD")], cancellable = true)
    private fun chat(string: String, ci: CallbackInfo) {
        val process: EventResult = ClientChatEvent.SEND.event.send(string, null)
        if (process.isFalse) {
            ci.cancel()
        }
    }
}
