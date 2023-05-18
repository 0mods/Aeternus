package com.algorithmlx.ancientmagic.mixin.compact;

import com.algorithmlx.ancientmagic.config.CommonConfiguration;
import com.algorithmlx.ancientmagic.init.Register;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.blay09.mods.waystones.block.WaystoneBlockBase;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.tag.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WaystoneBlock.class, remap = false)
public abstract class WaystoneInject extends WaystoneBlockBase {
    public WaystoneInject(Properties properties) { super(properties); } // shit on this class

    @Inject(method = "handleActivation", at = @At("HEAD"), cancellable = true)
    private void handleActivationInject(Level world, BlockPos pos, Player player, WaystoneBlockEntityBase tileEntity,
                                        IWaystone waystone, CallbackInfoReturnable<InteractionResult> cir) {
        var mainHand = player.getMainHandItem();

        if (mainHand.is(ModTags.BOUND_SCROLLS)) {
            cir.setReturnValue(InteractionResult.PASS);
        } else {
            if (mainHand.is(Register.MAGIC_DUST.get())) {
                if (CommonConfiguration.COMPACT_WAYSTONES != null)
                    mainHand.shrink(CommonConfiguration.CONSUME_DUST_COUNT.get());
                else mainHand.shrink(2);

                start(world, pos, player, tileEntity, waystone);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (mainHand.is(Register.LARGE_BRANCH_CRYSTAL.get())) {
                start(world, pos, player, tileEntity, waystone);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                player.sendSystemMessage(Component.translatable("compact.ancientmagic.waystones.no_essence"));
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }

        cir.cancel();
    }

    public void start(Level level, BlockPos pos, Player player, WaystoneBlockEntityBase blockEntity, IWaystone waystone) {
        boolean isActivated = PlayerWaystoneManager.isWaystoneActivated(player, waystone);
        if (isActivated) {
            if (!level.isClientSide) {
                if (WaystonesConfig.getActive().allowWaystoneToWaystoneTeleport()) {
                    Balm.getNetworking().openGui(player, blockEntity.getMenuProvider());
                } else {
                    player.displayClientMessage(
                            Component.translatable("chat.waystones.waystone_to_waystone_disabled"), true);
                }
            }
        } else {
            PlayerWaystoneManager.activateWaystone(player, waystone);
            if (!level.isClientSide) {
                MutableComponent nameComponent = Component.literal(waystone.getName());
                nameComponent.withStyle(ChatFormatting.WHITE);
                MutableComponent chatComponent =
                        Component.translatable("chat.waystones.waystone_activated", nameComponent);
                chatComponent.withStyle(ChatFormatting.YELLOW);
                player.sendSystemMessage(chatComponent);
                WaystoneSyncManager.sendActivatedWaystones(player);
                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.2F, 1.0F);
            }

            this.notifyObserversOfAction(level, pos);
            if (level.isClientSide) {
                for (int i = 0; i < 32; ++i) {
                    level.addParticle(ParticleTypes.ENCHANT, pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2.0, (pos.getY() + 3), pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2.0, 0.0, -5.0, 0.0);
                    level.addParticle(ParticleTypes.ENCHANT, pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2.0, (pos.getY() + 4), pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2.0, 0.0, -5.0, 0.0);
                }
            }
        }
    }
}
