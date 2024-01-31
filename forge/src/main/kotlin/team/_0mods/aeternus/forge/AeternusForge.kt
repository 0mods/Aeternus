package team._0mods.aeternus.forge

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.dimension.DimensionType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.RegisterEvent
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.registry.AeternusItems
import team._0mods.aeternus.init.registry.AeternusRegsitry
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.function.BiConsumer
import java.util.function.Consumer

@Mod(ModId)
class AeternusForge {

    private fun bindForItems(source: Consumer<BiConsumer<Item, ResourceLocation>>) {
        MOD_BUS.addListener<RegisterEvent> {
            if (it.registryKey.equals(Registries.ITEM)) {
                source.accept { obj, rl ->
                    it.register(Registries.ITEM, rl) { obj }
                }
            }
        }
    }
}