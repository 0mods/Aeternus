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

object AeternusRegsitry {
    private val items = linkedMapOf<ResourceLocation, Item>()
    private val blocks = linkedMapOf<ResourceLocation, Block>()

    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))

    val testBlock = register("test_block", Block(BlockBehaviour.Properties.of()))

    private fun <T: Block> register(id: String, obj: T, i: Item.Properties = Item.Properties()): T {
        val old = blocks.put(resloc(ModId, id), obj)

        if (old != null)
            throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")

        register(id, BlockItem(obj, i))

        return obj
    }

    private fun <T: Item> register(id: String, obj: T): T {
        val old = items.put(resloc(ModId, id), obj)
        if (old != null)
            throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
        return obj
    }

    fun handleItems(cons: BiConsumer<Item, ResourceLocation>) {
        items.entries.forEach { i ->
            cons.accept(i.value, i.key)
        }
    }

    fun handleBlocks(cons: BiConsumer<Block, ResourceLocation>) {
        blocks.entries.forEach {
            cons.accept(it.value, it.key)


        }
    }
}