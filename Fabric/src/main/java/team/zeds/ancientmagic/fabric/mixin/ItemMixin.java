package team.zeds.ancientmagic.fabric.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.zeds.ancientmagic.common.api.magic.IMagicItem;
import team.zeds.ancientmagic.common.api.util.ColorUtils;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getBarColor", at = @At("HEAD"), cancellable = true)
    public void barColorInj(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        final var item = itemStack.getItem();
        if (item instanceof IMagicItem) {
            if (itemStack.hasTag()) {
                var storage = ((IMagicItem) item).getMagicStorage(itemStack);
                cir.setReturnValue(ColorUtils.calcColorWithMana(storage.getManaStorage(), storage.getMaxManaStorage()));
            }
        }
    }

    @Inject(method = "isBarVisible", at = @At("HEAD"), cancellable = true)
    public void barVisibleInj(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        final var item = itemStack.getItem();
        if (item instanceof IMagicItem) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getBarWidth", at = @At("HEAD"), cancellable = true)
    public void barWidth(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        final var item = itemStack.getItem();
        if (item instanceof IMagicItem) {
            if (itemStack.hasTag()) {
                var storage = ((IMagicItem) item).getMagicStorage(itemStack);
                cir.setReturnValue(ColorUtils.calcStepWithMana(storage.getManaStorage(), storage.getMaxManaStorage()));
            }
        }
    }
}
