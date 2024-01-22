package team._0mods.aeternus.init.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import team._0mods.aeternus.service.ServiceProvider
import java.util.function.Supplier

fun <T: Item> registerItem(
    id: String,
    obj: (Item.Properties) -> T,
    props: Item.Properties = Item.Properties()
): Supplier<T> {
    if (AeternusItems.items.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
    return Supplier { obj(props) }
}

fun <T: Block> registerBlock(
    id: String,
    obj: (BlockBehaviour.Properties) -> T,
    props: BlockBehaviour.Properties = BlockBehaviour.Properties.of()
): Supplier<T> {
    if (AeternusBlocks.blocks.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
    return Supplier { obj(props) }
}

object AeternusRegsitry {
    val tab = ResourceKey.create(Registries.CREATIVE_MODE_TAB, resloc(ModId, "aeternus"))

    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))
}