package team._0mods.aeternus.forge.init

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import team._0mods.aeternus.ModId
import team._0mods.aeternus.init.registry.AeternusRegsitry

object AFRegistryHandler {
    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
    private val blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

    init {
        AeternusRegsitry.getBlocksForRegistry().entries.forEach {
            val id = it.key
            val block = it.value
            val reg = blocks.register(id) { block.apply(BlockBehaviour.Properties.of()) }
            items.register(id) { BlockItem(reg.get(), Item.Properties()) }
        }

        AeternusRegsitry.getItemsForRegistry().entries.forEach {
            val id = it.key
            val item = it.value
            items.register(id) { item.apply(Item.Properties()) }
        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }
}