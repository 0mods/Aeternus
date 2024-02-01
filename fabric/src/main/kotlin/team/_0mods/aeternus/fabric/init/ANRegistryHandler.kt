package team._0mods.aeternus.fabric.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import team._0mods.aeternus.ModId
import team._0mods.aeternus.init.registry.AeternusRegsitry
import team._0mods.aeternus.resloc

object ANRegistryHandler {
    fun init() {
        AeternusRegsitry.getBlocksForRegistry().entries.forEach {
            val rl = resloc(ModId, it.key)
            val blockNotInitialized = it.value
            val block = Registry.register(BuiltInRegistries.BLOCK, rl, blockNotInitialized.apply(BlockBehaviour.Properties.of()))
            Registry.register(BuiltInRegistries.ITEM, rl, BlockItem(block, Item.Properties()))
        }

        AeternusRegsitry.getItemsForRegistry().entries.forEach {
            val rl = resloc(ModId, it.key)
            val iNI = it.value
            Registry.register(BuiltInRegistries.ITEM, rl, iNI.apply(Item.Properties()))
        }
    }
}