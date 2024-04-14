package team._0mods.multilib.fabric.mixin

import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.animal.horse.AbstractHorse
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.PlayerEvent
import java.util.*

@Mixin(ServerPlayer::class)
class ServerPlayerMixin {
    @Inject(method = ["restoreFrom"], at = [At("RETURN")])
    private fun restoreFrom(serverPlayer: ServerPlayer, bl: Boolean, ci: CallbackInfo) {
        PlayerEvent.PLAYER_CLONE.event.clone(
            serverPlayer,
            this as ServerPlayer, bl
        )
    }

    @Inject(method = ["openMenu"], at = [At("RETURN")])
    private fun openMenu(menuProvider: MenuProvider, cir: CallbackInfoReturnable<OptionalInt>) {
        if (cir.returnValue.isPresent) {
            PlayerEvent.OPEN_MENU.event.open(
                this as ServerPlayer,
                (this as ServerPlayer).containerMenu
            )
        }
    }

    @Inject(method = ["openHorseInventory"], at = [At("RETURN")])
    private fun openHorseInventory(abstractHorse: AbstractHorse, container: Container, ci: CallbackInfo) {
        PlayerEvent.OPEN_MENU.event.open(
            this as ServerPlayer,
            (this as ServerPlayer).containerMenu
        )
    }

    @Inject(
        method = ["doCloseContainer"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;removed(Lnet/minecraft/world/entity/player/Player;)V",
            shift = At.Shift.AFTER
        )]
    )
    private fun doCloseContainer(ci: CallbackInfo) {
        PlayerEvent.CLOSE_MENU.event.close(
            this as ServerPlayer,
            (this as ServerPlayer).containerMenu
        )
    }

    @Inject(method = ["triggerDimensionChangeTriggers"], at = [At("HEAD")])
    private fun changeDimension(serverLevel: ServerLevel, ci: CallbackInfo) {
        PlayerEvent.CHANGE_DIMENSION.event.change(
            this as ServerPlayer, serverLevel.dimension(),
            (this as ServerPlayer).level().dimension()
        )
    }
}
