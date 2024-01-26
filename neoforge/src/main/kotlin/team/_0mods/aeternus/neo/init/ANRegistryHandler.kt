package team._0mods.aeternus.neo.init

import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusBlocks
import team._0mods.aeternus.init.registry.AeternusItems

object ANRegistryHandler {
    val items = DeferredRegister.createItems(ModId)
    val blocks = DeferredRegister.createBlocks(ModId)

    init {
        AeternusBlocks.getBlocksForRegistry().entries.forEach {
            val id = it.key
            val block = it.value
            val registeredBlock = blocks.registerBlock(id, block, BlockBehaviour.Properties.of())
            items.registerSimpleBlockItem(id, registeredBlock)
        }
        AeternusItems.getItemsForRegistry().entries.forEach {
            val id = it.key
            val item = it.value
            items.registerItem(id, item)
        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }
}
