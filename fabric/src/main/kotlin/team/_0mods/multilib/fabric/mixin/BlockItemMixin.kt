package team._0mods.multilib.fabric.mixin

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.state.BlockState
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.BlockEvent

@Mixin(BlockItem::class)
class BlockItemMixin {
    @Inject(
        method = ["place"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/BlockItem;placeBlock(Lnet/minecraft/world/item/context/BlockPlaceContext;Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )],
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private fun place(
        _0: BlockPlaceContext,
        cir: CallbackInfoReturnable<InteractionResult>,
        context: BlockPlaceContext,
        placedState: BlockState
    ) {
        val result =
            BlockEvent.PLACE.event.placeBlock(context.level, context.clickedPos, placedState, context.player)
        if (result.isFalse) {
            cir.setReturnValue(InteractionResult.FAIL)
        }
    }
}