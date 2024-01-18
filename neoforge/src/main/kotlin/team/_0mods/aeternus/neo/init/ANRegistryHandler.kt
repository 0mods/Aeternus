package team._0mods.aeternus.neo.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.RegisterEvent
import team._0mods.aeternus.init.LOGGER
import team._0mods.aeternus.init.registry.AeternusRegsitry
import team._0mods.aeternus.neo.api.modEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.BiConsumer
import java.util.function.Consumer

object ANRegistryHandler {
    @JvmStatic
    fun init(bus: IEventBus) {
        bind(Registries.ITEM, AeternusRegsitry::handleItems)
//        bind(Registries.BLOCK, AeternusRegsitry::handleBlocks)
    }

    @JvmStatic
    fun <T> bind(reg: ResourceKey<Registry<T>>, source: Consumer<BiConsumer<T, ResourceLocation>>) {
        MOD_BUS.addListener<RegisterEvent> {
            if (reg == it.registryKey)
                source.accept { value, id ->
                    LOGGER.debug("Registered {} with id: {}", reg.registry(), id)
                    it.register(reg, id) { value }
                }
        }
    }
}