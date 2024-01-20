package team._0mods.aeternus.neo.init

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import team._0mods.aeternus.init.LOGGER
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusBlock
import team._0mods.aeternus.init.registry.AeternusItems
import java.util.function.Supplier

object ANRegistryHandler {
    val items = DeferredRegister.createItems(ModId)
    val blocks = DeferredRegister.createBlocks(ModId)

    init {
        AeternusItems.getItemsForRegistry().entries.forEach {
            val id = it.key
            val item = it.value
            val itemRegistered = items.registerItem(id, item)
            LOGGER.info("Item {} has been registered!", itemRegistered.id)
        }

        AeternusBlock.getBlocksForRegistry().entries.forEach {
            val id = it.key
            val block = it.value
            val registered = blocks.register(id, Supplier { block })
            LOGGER.info("Block {} has been registered!", registered.id)
        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }
}
