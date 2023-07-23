package team.zeds.ancientmagic.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeds.ancientmagic.api.item.MagicBlockItem;
import team.zeds.ancientmagic.api.item.MagicItem;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.init.AMManage;
import team.zeds.ancientmagic.init.registries.AMCapability;
import team.zeds.ancientmagic.init.registries.AMNetwork;
import team.zeds.ancientmagic.init.registries.AMTags;
import team.zeds.ancientmagic.network.s2c.PlayerMagicDataSyncS2CPacket;

/**
 * @author AlgorithmLX
 */
public final class AMForgeEvents {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (stack.getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != MagicTypes.NOTHING)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));

            if (item.getMaxMana() != 0)
                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(e.getItemStack()),
                        item.getMaxMana()));

            var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            var namespace = resource.getNamespace();

            if (!namespace.equals(AMConstant.KEY))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", AMConstant.KEY), namespace));

            if (CompactInitializer.getWaystonesLoaded() && ((AMManage.COMMON_CONFIG.getCompactWaystones().get() != null
                    && AMManage.COMMON_CONFIG.getCompactWaystones().get()) && FMLEnvironment.production) &&
                    (stack.is(AMTags.getInstance().getUnconsumeCatalyst())
                            || stack.is(AMTags.getInstance().getConsumeCatalyst()))) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", AMConstant.KEY)));
            }
        }

        if (e.getItemStack().getItem() instanceof MagicBlockItem item) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != MagicTypes.NOTHING)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));

            if (item.getMaxMana() != 0)
                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(e.getItemStack()),
                        item.getMaxMana()));

            final var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            final var namespace = resource.getNamespace();

            if (!namespace.equals(AMConstant.KEY))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", AMConstant.KEY), namespace));
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        var stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof MagicItem item) {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap-> {
                if (cap.getMagicLevel() >= item.getBuilder().getMagicType().asLevel())
                    item.setItemUse(true);
                else {
                    if (player.level().isClientSide())
                        player.displayClientMessage(Component.translatable(
                                "magic.ancientmagic.notLevel",
                                item.getMagicType().getTranslation(),
                                MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()
                        ), true);
                    item.setItemUse(false);
                }
            });
        }

        if (stack.getItem() instanceof MagicBlockItem item) {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap-> {
                if (cap.getMagicLevel() >= item.getBuilder().getMagicType().asLevel())
                    item.setItemUse(true);

                else {
                    if (player.level().isClientSide())
                        player.displayClientMessage(Component.translatable(
                                "magic.ancientmagic.notLevel",
                                item.getMagicType().getTranslation(),
                                MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()
                        ), true);
                    item.setItemUse(false);
                }
            });
        }

        if (!event.player.level().isClientSide())
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                        AMNetwork.sendToPlayer(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), serverPlayer));
            }
    }
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath())
            event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(old ->
                    event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER)
                            .ifPresent(newCap -> newCap.copyFrom(old)));

    }
    @SubscribeEvent
    public static void playerConnectToWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                AMConstant.LOGGER.debug("Player {} connected!", event.getEntity());

                player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                        AMNetwork.sendToPlayer(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), player)
                );
            }
        }
    }
}
