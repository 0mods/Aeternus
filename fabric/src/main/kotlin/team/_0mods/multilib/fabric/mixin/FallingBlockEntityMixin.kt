package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.BlockEvent

@Mixin(FallingBlockEntity::class)
abstract class FallingBlockEntityMixin(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {

    @Shadow
    private lateinit var blockState: BlockState

    @Inject(
        method = ["tick"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Fallable;onLand(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/item/FallingBlockEntity;)V"
        )],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    fun handleLand(
        ci: CallbackInfo,
        block: Block,
        blockPos2: BlockPos,
        bl: Boolean,
        bl2: Boolean,
        d: Double,
        blockState: BlockState
    ) {
        val obj = this as Any

        BlockEvent.FALLING_LAND.event.onLand(
            this.level(), blockPos2,
            this.blockState, blockState,
            obj as FallingBlockEntity
        )
    }
}