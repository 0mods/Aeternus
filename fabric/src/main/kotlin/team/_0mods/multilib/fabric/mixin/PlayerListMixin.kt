package team._0mods.multilib.fabric.mixin

import net.minecraft.network.Connection
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.CommonListenerCookie
import net.minecraft.server.players.PlayerList
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(PlayerList::class)
class PlayerListMixin {
    @Inject(method = ["placeNewPlayer"], at = [At("RETURN")])
    private fun placeNewPlayer(
        connection: Connection,
        serverPlayer: ServerPlayer,
        commonListenerCookie: CommonListenerCookie,
        ci: CallbackInfo
    ) {
        PlayerEvent.PLAYER_JOIN.event.join(serverPlayer)
    }

    @Inject(method = ["remove"], at = [At("HEAD")])
    private fun remove(serverPlayer: ServerPlayer, ci: CallbackInfo) {
        PlayerEvent.PLAYER_QUIT.event.quit(serverPlayer)
    }

    @Inject(method = ["respawn"], at = [At("RETURN")])
    private fun respawn(serverPlayer: ServerPlayer, bl: Boolean, cir: CallbackInfoReturnable<ServerPlayer>) {
        PlayerEvent.PLAYER_RESPAWN.event.respawn(cir.returnValue, bl)
    }
}
