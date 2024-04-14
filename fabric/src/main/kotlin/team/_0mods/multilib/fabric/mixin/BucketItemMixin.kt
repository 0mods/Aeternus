package team._0mods.multilib.fabric.mixin

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(BucketItem::class)
class BucketItemMixin {
    @Inject(
        method = ["use"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/BlockHitResult;getType()Lnet/minecraft/world/phys/HitResult\$Type;",
            ordinal = 0
        )],
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    fun fillBucket(
        level: Level,
        player: Player,
        hand: InteractionHand,
        cir: CallbackInfoReturnable<InteractionResultHolder<ItemStack>>,
        stack: ItemStack,
        target: BlockHitResult
    ) {
        val result = PlayerEvent.FILL_BUCKET.event.fill(player, level, stack, target)
        if (result.endFurtherEvaluation && result.value != null) {
            cir.setReturnValue(result.asInteractionResult)
            cir.cancel()
        }
    }
}
