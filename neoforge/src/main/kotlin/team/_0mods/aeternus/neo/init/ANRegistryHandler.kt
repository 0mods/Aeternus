package team._0mods.aeternus.neo.init

import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import team._0mods.aeternus.init.LOGGER
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusBlocks
import team._0mods.aeternus.init.registry.AeternusItems
import team._0mods.aeternus.init.registry.AeternusRegsitry
import team._0mods.aeternus.neo.api.tab.AddItemToTabHandler
import team._0mods.aeternus.service.ServiceProvider
import java.util.function.Supplier

object ANRegistryHandler {
    private val registeredEntries = mutableListOf<Supplier<out ItemLike>>()
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
