package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.capability.PlayerMagicCapability;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.init.AMNetwork;
import team.zeromods.ancientmagic.init.config.AMCommon;
import team.zeromods.ancientmagic.init.AMTags;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;
import team.zeromods.ancientmagic.network.PlayerMagicDataSyncS2CPacket;

import java.util.ServiceLoader;

public class MagicData {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            if (item.getMagicType() != null)
                tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != null)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));

            if (item.getMaxMana(stack, null) != 0)
                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(stack, null),
                        item.getMaxMana(stack, null)));

            var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.Key))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.Key), namespace));

            if (CompactInitializer.getWaystonesLoaded() && ((AMCommon.COMPACT_WAYSTONES != null
                    && AMCommon.COMPACT_WAYSTONES.get()) && FMLEnvironment.production) &&
                    (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                    || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST))) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.Key)));
            }
        }
    }

    public static void registerCapability(final RegisterCapabilitiesEvent e) {
        e.register(PlayerMagicCapability.Wrapper.class);
    }

    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath())
            event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(old ->
                    event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER)
                            .ifPresent(newCap -> newCap.copyFrom(old)));

    }

    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).isPresent())
                event.addCapability(AMCapability.PLAYER_MAGIC_HANDLER_ID, new PlayerMagicCapability.Provider());
        }
    }

    public static void playerTick(final TickEvent.PlayerTickEvent e) {
        if (e.side == LogicalSide.SERVER) {
            e.player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                AMNetwork.sendToClient(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), (ServerPlayer) e.player)
            );
        }
    }

    public static void playerConnectToWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                    AMNetwork.sendToClient(new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()), player)
                );
            }
        }
    }

    public static <T> T interfaceCallback(Class<T> convert) {
        final T callback = ServiceLoader.load(convert).findFirst()
                .orElseThrow(()-> new NullPointerException(String.format("Failed to call interface for: %s", convert.getName())));
        Constant.LOGGER.debug("Callback {} interface from {}", callback, convert);
        return callback;
    }
}
