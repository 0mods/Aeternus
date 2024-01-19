package team._0mods.aeternus.neo

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.RegisterEvent
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusItems
import team._0mods.aeternus.init.registry.AeternusRegsitry
import team._0mods.aeternus.neo.api.tab.AddItemToTabHandler
import thedarkcolour.kotlinforforge.neoforge.KotlinModLoadingContext
import java.util.function.BiConsumer
import java.util.function.Consumer

@Mod(ModId)
class AeternusNeo(private val bus: IEventBus) {
    init {
        initRegistry()
        AddItemToTabHandler.init()
    }

    private fun initRegistry() {
        bindForItems(AeternusItems::handleItems)
    }

    private fun <T> bind(registry: ResourceKey<Registry<T>>, source: Consumer<BiConsumer<T, ResourceLocation>>) {
        KotlinModLoadingContext.get().getKEventBus().addListener<RegisterEvent> {
            if (registry.equals(it.registryKey)) {
                source.accept { obj, rl ->
                    it.register(registry, rl) { obj }
                }
            }
        }
    }

    private fun bindForItems(source: Consumer<BiConsumer<Item, ResourceLocation>>) {
        this.bus.addListener<RegisterEvent> {
            if (it.registryKey.equals(Registries.ITEM)) {
                source.accept { obj, rl ->
                    AddItemToTabHandler.mapOfItemsAndTabs[obj] = AeternusRegsitry.tab
                    it.register(Registries.ITEM, rl) { obj }
                }
            }
        }
    }
}
