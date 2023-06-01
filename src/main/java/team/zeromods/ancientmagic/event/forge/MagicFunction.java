package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.mod.Constant;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.config.AMCommon;
import team.zeromods.ancientmagic.init.AMTags;

public class MagicFunction {
    public static final EntityDataAccessor<CompoundTag> MODIFICATE_TAG = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);

    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            if (item.getMagicType() != null)
                tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()).withStyle(item.getMagicType().getStyle()));
            if (item.getMagicSubtype() != null)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype()
                                .getTranslation().withStyle(item.getMagicSubtype().getStyle())).withStyle(ChatFormatting.DARK_BLUE));

            if (item.getMaxMana(stack, null) != 0)
                tooltip.add(item.getMagicType().getMagicTooltip("storage", item.getStorageMana(stack, null),
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

    public static void playerEvent(PlayerEvent event) {
        var stack = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        var tag = new CompoundTag();

        if (stack.getItem() instanceof MagicItem item) {

        }
    }
}
