package team.zeds.ancientmagic.event

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import team.zeds.ancientmagic.AMConstant
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.magic.MagicType.Companion.getMagicMessage
import team.zeds.ancientmagic.api.magic.MagicTypes
import java.lang.String

class AMCommonnessEvents { //Prototype of AMForgeEvents
    fun tooltipEvent(stack: ItemStack, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val item = stack.item

        if (item is MagicItem) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()))
            if (item.getMagicSubtype() !== MagicTypes.NOTHING) tooltip.add(
                Component.translatable(
                    "magicType.subtype",
                    item.getMagicSubtype().getTranslation()
                )
            )

            if (item.getMaxMana() != 0) tooltip.add(
                getMagicMessage(
                    "storage", item.getManaStorages(stack),
                    item.getMaxMana()
                )
            )

            val resource: ResourceLocation = BuiltInRegistries.ITEM.getKey(item)
            val namespace = resource.namespace

            if (namespace != AMConstant.KEY) tooltip.add(
                Component.translatable(
                    String.format(
                        "content.%s.added_by",
                        AMConstant.KEY
                    ), namespace
                )
            )

//            if (CompactInitializer.getWaystonesLoaded() && (AMManage.COMMON_CONFIG.getCompactWaystones().get() != null
//                        && AMManage.COMMON_CONFIG.getCompactWaystones().get()) && FMLEnvironment.production &&
//                (stack.`is`(AMTags.getInstance().getUnconsumeCatalyst())
//                        || stack.`is`(AMTags.getInstance().getConsumeCatalyst()))
//            ) {
//                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", AMConstant.KEY)))
//            }
        }
    }
}