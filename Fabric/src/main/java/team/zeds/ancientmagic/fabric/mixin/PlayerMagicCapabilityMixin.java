package team.zeds.ancientmagic.fabric.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.zeds.ancientmagic.fabric.util.EntityDataHolder;

@Mixin(Entity.class)
public class PlayerMagicCapabilityMixin implements EntityDataHolder {
    @Unique
    private CompoundTag persistentData;

    @Override
    public CompoundTag getPersistentData() {
        if (persistentData == null) persistentData = new CompoundTag();
        return persistentData;
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void injectSave(CompoundTag compoundTag, CallbackInfoReturnable<Boolean> cir) {
        if (persistentData != null) compoundTag.put("AncientMagicData", persistentData);
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void injectLoad(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("AncientMagicData")) persistentData = compoundTag.getCompound("AncientMagicData");
    }
}
