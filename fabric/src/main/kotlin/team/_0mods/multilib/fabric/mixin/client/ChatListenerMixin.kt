package team._0mods.multilib.fabric.mixin.client

import com.mojang.authlib.GameProfile
import net.minecraft.client.multiplayer.chat.ChatListener
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.ChatType.Bound
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.PlayerChatMessage
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyVariable
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.client.ClientChatEvent
import team._0mods.multilib.event.base.client.ClientSystemMessageEvent
import java.util.Objects

@Mixin(ChatListener::class)
class ChatListenerMixin {
    @Unique
    private var boundChatType: Bound? = null

    @Unique
    private var cancelNextChat: ThreadLocal<Component> = ThreadLocal()

    @Unique
    private var cancelNextSystem: ThreadLocal<Component> = ThreadLocal()

    @Inject(
        method = ["handlePlayerChatMessage"],
        at = [At(value = "INVOKE", target = "Ljava/time/Instant;now()Ljava/time/Instant;")]
    )
    private fun handlePlayerChatMsg(chatMsg: PlayerChatMessage, gp: GameProfile, bound: Bound, ci: CallbackInfo) {
        this.boundChatType = bound
    }

    @ModifyVariable(method = ["handlePlayerChatMessage"], at = At(value = "INVOKE", target = "Lnet/minecraft/network/chat/PlayerChatMessage;signature()Lnet/minecraft/network/chat/MessageSignature;"))
    private fun modifyMsg(value: Component): Component {
        cancelNextChat.remove()
        val process = ClientChatEvent.RECEIVED.event.process(boundChatType, value)
        boundChatType = null
        if (process.isPresent) {
            if (process.isFalse) cancelNextChat.set(value)
            else if (process.obj != null) return process.obj!!
        }

        return value
    }

    @Inject(method = ["handlePlayerChatMessage"], at = [At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleMessage(Lnet/minecraft/network/chat/MessageSignature;Ljava/util/function/BooleanSupplier;)V")],
        cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private fun handlePre(pcm: PlayerChatMessage, gp: GameProfile, bound: Bound, onlySecureShow: Boolean, filtered: PlayerChatMessage, component: Component, ci: CallbackInfo) {
        if (Objects.equals(cancelNextChat.get(), component))
            ci.cancel()

        cancelNextChat.remove()
    }

    @ModifyVariable(method = ["handleSystemMessage"], at = At(value = "HEAD"), argsOnly = true)
    private fun modSysMsg(msg: Component): Component {
        cancelNextSystem.remove()
        val process = ClientSystemMessageEvent.RECEIVED.event.process(msg)
        if (process.isPresent) {
            if (process.isFalse) cancelNextSystem.set(msg)
            else if (process.obj != null) return process.obj!!
        }

        return msg
    }

    @Inject(method = ["handleSystemMessage"], at = [At(value = "INVOKE", target = "Lnet/minecraft/client/Options;hideMatchedNames()Lnet/minecraft/client/OptionInstance;")], cancellable = true)
    fun handleSysMsg(component: Component, bool: Boolean, ci: CallbackInfo) {
        if (Objects.equals(cancelNextSystem.get(), component))
            ci.cancel()

        cancelNextSystem.remove()
    }
}
