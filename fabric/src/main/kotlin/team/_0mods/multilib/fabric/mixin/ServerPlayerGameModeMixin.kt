package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.level.ServerPlayerGameMode
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.BlockEvent

@Mixin(ServerPlayerGameMode::class)
class ServerPlayerGameModeMixin {
    @Shadow
    protected lateinit var level: ServerLevel

    @Shadow
    @Final
    protected lateinit var player: ServerPlayer

    @Inject(
        method = ["destroyBlock"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;",
            ordinal = 0
        )],
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private fun onBreak(
        blockPos: BlockPos,
        cir: CallbackInfoReturnable<Boolean>,
        entity: BlockEntity,
        state: BlockState
    ) {
        if (BlockEvent.BREAK.event.breakBlock(
                this.level, blockPos, state,
                this.player, null
            ).isFalse
        ) {
            cir.setReturnValue(false)
        }
    }
}