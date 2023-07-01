package team.zeds.ancientmagic.mixin.compact;

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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.compact.curios.events.RetraceStoneEvent;
import team.zeds.ancientmagic.init.AMManage;
import team.zeds.ancientmagic.init.registries.AMRegister;
import team.zeds.ancientmagic.init.registries.AMTags;
import team.zeds.ancientmagic.item.RetraceStone;

@SuppressWarnings("ALL")
@Mixin(value = WaystoneBlock.class, remap = false)
public abstract class WaystoneInject extends WaystoneBlockBase {
    public WaystoneInject(Properties properties) { super(properties); }

    @Inject(method = "handleActivation", at = @At("HEAD"), cancellable = true)
    private void handleActivationInject(Level world, BlockPos pos, Player player, WaystoneBlockEntityBase tileEntity,
                                        IWaystone waystone, CallbackInfoReturnable<InteractionResult> cir) {
        var mainHand = player.getMainHandItem();

        if (mainHand.is(ModTags.BOUND_SCROLLS)) {
            cir.setReturnValue(InteractionResult.PASS);
        } else {
            if (((AMManage.COMMON_CONFIG.COMPACT_WAYSTONES != null && AMManage.COMMON_CONFIG.COMPACT_WAYSTONES.get())
                    && FMLEnvironment.production)) {
                if ((mainHand.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST))) {
                    if (AMManage.COMMON_CONFIG.CONSUME_DUST_COUNT_ON_TELEPORT != null)
                        mainHand.shrink(AMManage.COMMON_CONFIG.CONSUME_DUST_COUNT_ON_TELEPORT.get());
                    else mainHand.shrink(2);

                    start(world, pos, player, tileEntity, waystone);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if (
                        (mainHand.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)
                                || CompactInitializer.getCuriosLoaded() && RetraceStoneEvent.isEquip
                                && RetraceStone.getActive(new ItemStack(AMRegister.RETRACE_CRYSTAL.get())))
                ) {
                    start(world, pos, player, tileEntity, waystone);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if ((mainHand.isEmpty() || !(mainHand.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)
                        || mainHand.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)))
                ) {
                    player.displayClientMessage(Component.translatable("compact.ancientmagic.waystones.no_essence"), true);
                    cir.setReturnValue(InteractionResult.FAIL);
                }
            } else if (((AMManage.COMMON_CONFIG.COMPACT_WAYSTONES != null && !AMManage.COMMON_CONFIG.COMPACT_WAYSTONES.get()) && FMLEnvironment.production)) {
                start(world, pos, player, tileEntity, waystone);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (AMManage.COMMON_CONFIG.COMPACT_WAYSTONES == null) {
                start(world, pos, player, tileEntity, waystone);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (!FMLEnvironment.production) {
                var id = ForgeRegistries.ITEMS.getKey(AMRegister.RETRACE_CRYSTAL.get()).getPath();

                if (!id.equals("retrace_stone")) {
                    if ((mainHand.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST))) {
                        if (AMManage.COMMON_CONFIG.CONSUME_DUST_COUNT_ON_TELEPORT != null)
                            mainHand.shrink(AMManage.COMMON_CONFIG.CONSUME_DUST_COUNT_ON_TELEPORT.get());
                        else mainHand.shrink(2);

                        start(world, pos, player, tileEntity, waystone);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                    } else if (
                            (mainHand.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)
                                    || CompactInitializer.getCuriosLoaded() && RetraceStoneEvent.isEquip
                                    && RetraceStone.getActive(new ItemStack(AMRegister.RETRACE_CRYSTAL.get())))
                    ) {
                        start(world, pos, player, tileEntity, waystone);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                    } else if ((mainHand.isEmpty() || !(mainHand.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)
                            || mainHand.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)))
                    ) {
                        player.displayClientMessage(Component.translatable("compact.ancientmagic.waystones.no_essence"), true);
                        cir.setReturnValue(InteractionResult.FAIL);
                    }
                } else {
                    start(world, pos, player, tileEntity, waystone);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }

        cir.cancel();
    }

    public void start(Level level, BlockPos pos, Player player, WaystoneBlockEntityBase blockEntity, IWaystone waystone) {
        boolean isActivated = PlayerWaystoneManager.isWaystoneActivated(player, waystone);
        if (isActivated) {
            if (!level.isClientSide) {
                if (WaystonesConfig.getActive().restrictions.allowWaystoneToWaystoneTeleport) {
                    Balm.getNetworking().openGui(player, blockEntity.getMenuProvider());
                } else {
                    player.displayClientMessage(Component.translatable("chat.waystones.waystone_to_waystone_disabled"), true);
                }
            }
        } else {
            PlayerWaystoneManager.activateWaystone(player, waystone);

            if (!level.isClientSide) {
                var nameComponent = Component.literal(waystone.getName());
                nameComponent.withStyle(ChatFormatting.WHITE);
                var chatComponent = Component.translatable("chat.waystones.waystone_activated", nameComponent);
                chatComponent.withStyle(ChatFormatting.YELLOW);
                player.sendSystemMessage(chatComponent);

                WaystoneSyncManager.sendActivatedWaystones(player);

                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.2f, 1f);
            }

            notifyObserversOfAction(level, pos);

            if (level.isClientSide) {
                for (int i = 0; i < 32; i++) {
                    level.addParticle(ParticleTypes.ENCHANT,
                            pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            pos.getY() + 3,
                            pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            0,
                            -5,
                            0);
                    level.addParticle(ParticleTypes.ENCHANT,
                            pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            pos.getY() + 4,
                            pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            0,
                            -5,
                            0);
                }
            }
        }
    }
}
