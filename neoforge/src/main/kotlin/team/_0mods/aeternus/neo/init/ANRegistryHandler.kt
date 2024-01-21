package team._0mods.aeternus.neo.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegisterEvent
import team._0mods.aeternus.init.LOGGER
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusBlock
import team._0mods.aeternus.init.registry.AeternusItems
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

object ANRegistryHandler {
    val items = DeferredRegister.createItems(ModId)
    val blocks = DeferredRegister.createBlocks(ModId)

    init {
        AeternusBlock.getBlocksForRegistry().entries.forEach {
            val id = it.key
            val block = it.value
            blocks.register(id, Supplier { block })
        }
        AeternusItems.getItemsForRegistry().entries.forEach {
            val id = it.key
            val item = it.value
            val itemRegistered = items.registerItem(id, item)
            LOGGER.info("Item {} has been registered!", itemRegistered.id)
        }

//        AeternusBlock.getBlocksForRegistry().entries.forEach {
//            val id = it.key
//            val block = it.value
//            val registered = blocks.registerBlock(id, block, BlockBehaviour.Properties.of())
//            LOGGER.info("Block {} has been registered!", registered.id)
//        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }
}
