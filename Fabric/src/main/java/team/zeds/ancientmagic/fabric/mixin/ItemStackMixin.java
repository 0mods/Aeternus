package team.zeds.ancientmagic.fabric.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.zeds.ancientmagic.common.api.magic.IMagicItem;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IMagicItem {
    @Shadow public abstract Item getItem();

    @Inject(method = "save", at = @At("TAIL"))
    public void saveInj(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir) {
        final var item = this.getItem();

        if (item instanceof IMagicItem) {
            this.saveToCompound(compoundTag);
        }
    }
}
