package team.zeds.ancientmagic.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.zeds.ancientmagic.api.block.MagicBlockEntity;
import team.zeds.ancientmagic.api.cap.BlockMana;
import team.zeds.ancientmagic.api.item.MagicBlockItem;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.capability.BlockManaCapability;
import team.zeds.ancientmagic.capability.PlayerMagicCapability;
import team.zeds.ancientmagic.init.AMManage;
import team.zeds.ancientmagic.network.s2c.PlayerMagicDataSyncS2CPacket;
import team.zeds.ancientmagic.api.item.MagicItem;
import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeds.ancientmagic.init.registries.AMCapability;
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.init.registries.AMNetwork;
import team.zeds.ancientmagic.init.registries.AMTags;

public class AMMagicSetup {
    public static final BlockManaCapability.Provider BLOCK_MANA_CAPABILITY = new BlockManaCapability.Provider();

    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != MagicTypes.NOTHING)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));

            if (item.getMaxMana() != 0)
                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(e.getItemStack()),
                        item.getMaxMana()));

            var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.KEY))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.KEY), namespace));

            if (CompactInitializer.getWaystonesLoaded() && ((AMManage.COMMON_CONFIG.COMPACT_WAYSTONES.get() != null
                    && AMManage.COMMON_CONFIG.COMPACT_WAYSTONES.get()) && FMLEnvironment.production) &&
                    (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                    || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST))) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.KEY)));
            }
        }
    }

    public static void attachCapabilityToPlayer(final AttachCapabilitiesEvent<Player> event) {
        if (!event.getObject().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).isPresent())
            event.addCapability(rl("player_magic"), new PlayerMagicCapability.Provider());
    }

    public static void attachCapabilityToBlockEntity(final AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof MagicBlockEntity) {
            if (!event.getObject().getCapability(AMCapability.BLOCK_MAGIC_CAPABILITY).isPresent())
                event.addCapability(rl("block_mana"), BLOCK_MANA_CAPABILITY);
        }
    }

    public static void registerCapability(final RegisterCapabilitiesEvent e) {
        e.register(PlayerMagicCapability.class);
        e.register(BlockManaCapability.class);
    }

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
    private static ResourceLocation rl(String name)  {
        return new ResourceLocation(Constant.KEY, name);
    }
}
