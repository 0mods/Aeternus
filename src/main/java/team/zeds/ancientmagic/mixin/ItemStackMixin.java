package team.zeds.ancientmagic.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.zeds.ancientmagic.api.item.MagicItem;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;)V", at = @At("TAIL"))
    public void initInj(ItemLike p_41599_, CallbackInfo ci) {
        if (p_41599_ instanceof MagicItem item) {
            item.setStack(new ItemStack(p_41599_));
        }
    }
}
