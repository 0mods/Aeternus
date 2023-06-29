package team.zeds.ancientmagic.event;

import net.minecraft.world.item.ItemStack;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.capability.PlayerMagicCapability;
import team.zeds.ancientmagic.client.packet.ClientPlayerMagicData;
import team.zeds.ancientmagic.network.s2c.PlayerMagicDataSyncS2CPacket;
import team.zeds.ancientmagic.api.MagicItem;
import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeds.ancientmagic.capability.MagicObjectCapability;
import team.zeds.ancientmagic.init.AMCapability;
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.init.AMNetwork;
import team.zeds.ancientmagic.init.config.AMCommonOld;
import team.zeds.ancientmagic.init.AMTags;

public class AMMagicSetup {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
//            if (item.getMagicType() != null)
//                tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
//            if (item.getMagicSubtype() != null)
//                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));
//
//            if (item.getMaxMana(stack, null) != 0)
//                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(stack, null),
//                        item.getMaxMana(stack, null)));
            e.getItemStack().getCapability(AMCapability.MAGIC_OBJECT).ifPresent(cap -> {
                if (cap.getMagicType() != null)
                    tooltip.add(Component.translatable("magicType.type", cap.getMagicType().getTranslation()));
                if (cap.getMagicSubtype() != null)
                    tooltip.add(Component.translatable("magicType.subtype", cap.getMagicSubtype().getTranslation()));

                if (cap.getMaxMana() != 0)
                    tooltip.add(MagicType.getMagicMessage("storage", cap.getStorageMana(),
                            cap.getMaxMana()));
            });

            var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.KEY))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.KEY), namespace));

            if (CompactInitializer.getWaystonesLoaded() && ((AMCommonOld.COMPACT_WAYSTONES != null
                    && AMCommonOld.COMPACT_WAYSTONES.get()) && FMLEnvironment.production) &&
                    (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                    || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST))) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.KEY)));
            }
        }
    }

    public static void attachCapability(final AttachCapabilitiesEvent<Object> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).isPresent())
                event.addCapability(AMCapability.PLAYER_MAGIC_HANDLER_ID, new PlayerMagicCapability.Provider());
        }

        if (event.getObject() instanceof ItemStack stack) {
            if (stack.getItem() instanceof MagicItem) {
                event.addCapability(AMCapability.MAGIC_OBJECT_ID, new MagicObjectCapability.Provider(stack));
            }
        }
    }

    public static void playerEvent(PlayerEvent event) {
        var player = event.getEntity();

        if (player != null) {
            var stack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() instanceof MagicItem item) {
                var magicLevel = ClientPlayerMagicData.getPlayerData();
//                if (magicLevel >= (Objects.requireNonNull(item.getMagicType()).numerate())) {
//                    item.canUseItem = true;
//                } else {
//                    player.displayClientMessage(MagicType.getMagicMessage("notLevel",
//                                    item.getMagicType().getTranslation(),
//                                    MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData()).getTranslation()),
//                            true);
//                    item.canUseItem = false;
//                }
                stack.getCapability(AMCapability.MAGIC_OBJECT).ifPresent(cap -> {
                    if (cap.getMagicType() != null) {
                        if (magicLevel >= cap.getMagicType().numerate()) {
                            item.setItemUse(true);
                        } else {
                            player.displayClientMessage(MagicType.getMagicMessage("notLevel",
                                            cap.getMagicType().getTranslation(),
                                            MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData()).getTranslation()),
                                    true);
                            item.setItemUse(false);
                        }
                    }
                });
            }
        }
    }

    public static void registerCapability(final RegisterCapabilitiesEvent e) {
        e.register(PlayerMagicCapability.class);
        e.register(MagicObjectCapability.class);
    }

    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath())
            event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(old ->
                    event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER)
                            .ifPresent(newCap -> newCap.copyFrom(old)));

    }

    public static void playerConnectToWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                    AMNetwork.sendToPlayer(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), player)
                );
            }
        }
    }
    public static void playerTickEvent(TickEvent e) {
        if (e instanceof TickEvent.PlayerTickEvent event)
            if (!event.player.level().isClientSide()) {
                if (event.player instanceof ServerPlayer player) {
                    player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                            AMNetwork.sendToPlayer(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), player));
                }
            }
        if (e instanceof TickEvent.LevelTickEvent event) {
            event.level.getCapability(AMCapability.MAGIC_OBJECT).ifPresent(cap -> {
                var stack = cap.getStack();

                if (stack.getItem() instanceof MagicItem item) {
                    if (cap.getMagicType() == null)
                        cap.setMagicType(item.getBuilder().getMagicType());
                    if (cap.getMagicSubtype() == null)
                        if (item.getBuilder().getMagicSubtype() != null)
                            cap.setMagicSubtype(item.getBuilder().getMagicSubtype());
                    if (cap.getMaxMana() == 0)
                        if (item.getBuilder().getMaxMana() != 0)
                            cap.setMaxMana(item.getBuilder().getMaxMana());
                }
            });
        }
    }
}
