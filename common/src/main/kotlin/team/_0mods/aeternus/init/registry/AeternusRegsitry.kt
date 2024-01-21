package team._0mods.aeternus.init.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import java.util.function.BiConsumer
import java.util.function.Supplier

fun <T: Item> register(id: String, obj: (Item.Properties) -> T, props: Item.Properties = Item.Properties()): Supplier<T> {
    if (AeternusItems.items.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
    return Supplier { obj.invoke(props) }
}

fun <T: Block> register(
    id: String,
    obj: T
): T {
    if (AeternusBlock.blocks.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")

    return obj
}

object AeternusRegsitry {
    val tab = ResourceKey.create(Registries.CREATIVE_MODE_TAB, resloc(ModId, "aeternus"))

    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))
}