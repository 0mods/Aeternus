package team._0mods.aeternus.neo.init

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import team._0mods.aeternus.init.LOGGER
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusItems
import java.util.function.Supplier

object ANRegistryHandler {
    val items = DeferredRegister.createItems(ModId)
    val blocks = DeferredRegister.createBlocks(ModId)

    init {
        AeternusItems.items.entries.forEach {
            val id = it.key
            val item = it.value
            val itemRegistered = items.registerItem(id, item)
            LOGGER.info("Item {} has been registered!", itemRegistered.id)
        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }

    fun <T> toSup(obj: T): Supplier<T> =  kSupToJSupplier { obj }

    fun <T> kSupToJSupplier(obj: () -> T): Supplier<T> = Supplier { obj.invoke() }
}
