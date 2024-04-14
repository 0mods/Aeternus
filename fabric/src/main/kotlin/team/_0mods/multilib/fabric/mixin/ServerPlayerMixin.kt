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
        val obj = this as Any
        PlayerEvent.PLAYER_CLONE.event.clone(
            serverPlayer,
            obj as ServerPlayer, bl
        )
    }

    @Inject(method = ["openMenu"], at = [At("RETURN")])
    private fun openMenu(menuProvider: MenuProvider, cir: CallbackInfoReturnable<OptionalInt>) {
        val obj = this as Any
        if (cir.returnValue.isPresent) {
            PlayerEvent.OPEN_MENU.event.open(
                obj as ServerPlayer,
                obj.containerMenu
            )
        }
    }

    @Inject(method = ["openHorseInventory"], at = [At("RETURN")])
    private fun openHorseInventory(abstractHorse: AbstractHorse, container: Container, ci: CallbackInfo) {
        val obj = this as Any
        PlayerEvent.OPEN_MENU.event.open(
            obj as ServerPlayer,
            obj.containerMenu
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
        val obj = this as Any
        PlayerEvent.CLOSE_MENU.event.close(
            obj as ServerPlayer,
            obj.containerMenu
        )
    }

    @Inject(method = ["triggerDimensionChangeTriggers"], at = [At("HEAD")])
    private fun changeDimension(serverLevel: ServerLevel, ci: CallbackInfo) {
        val obj = this as Any
        PlayerEvent.CHANGE_DIMENSION.event.change(
            obj as ServerPlayer, serverLevel.dimension(),
            obj.level().dimension()
        )
    }
}
