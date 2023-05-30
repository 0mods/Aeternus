package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.mod.Constant;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.config.AMCommon;
import team.zeromods.ancientmagic.init.AMTags;

public class MagicFunction {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.type", ((MutableComponent) item.getMagicType()
                    .getTranslation()).withStyle(ChatFormatting.BLUE)).withStyle(item.getMagicType().getStyle()));
            if (item.getMagicSubtype() != null)
                tooltip.add(Component.translatable("magicType.subtype", ((MutableComponent) item.getMagicSubtype()
                                .getTranslation()).withStyle(ChatFormatting.DARK_BLUE)).withStyle(item.getMagicSubtype().getStyle())
                        .withStyle());

            if (item.maxMana(stack) != 0)
                tooltip.add(item.getMagicType().getMagicTooltip("storage", item.getStorageMana(stack)));

            var resource = ForgeRegistries.ITEMS.getKey(item);
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
}
