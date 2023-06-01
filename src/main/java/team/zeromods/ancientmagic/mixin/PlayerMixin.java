package team.zeromods.ancientmagic.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.zeromods.ancientmagic.event.forge.MagicFunction;

@Mixin(value = Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Shadow @Final
    private static EntityDataAccessor<Float> DATA_PLAYER_ABSORPTION_ID;
    @Shadow @Final
    private static EntityDataAccessor<Integer> DATA_SCORE_ID;
    @Shadow @Final
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION;
    @Shadow @Final
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;
    @Shadow @Final
    protected static EntityDataAccessor<CompoundTag> DATA_SHOULDER_LEFT;
    @Shadow @Final
    protected static EntityDataAccessor<CompoundTag> DATA_SHOULDER_RIGHT;

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    public void defneSynchedDataInject(CallbackInfo ci) {
        this.entityData.define(DATA_PLAYER_ABSORPTION_ID, 0.0F);
        this.entityData.define(DATA_SCORE_ID, 0);
        this.entityData.define(DATA_PLAYER_MODE_CUSTOMISATION, (byte)0);
        this.entityData.define(DATA_PLAYER_MAIN_HAND, (byte)1);
        this.entityData.define(DATA_SHOULDER_LEFT, new CompoundTag());
        this.entityData.define(DATA_SHOULDER_RIGHT, new CompoundTag());
        this.entityData.define(MagicFunction.MODIFICATE_TAG, new CompoundTag());
    }
}
