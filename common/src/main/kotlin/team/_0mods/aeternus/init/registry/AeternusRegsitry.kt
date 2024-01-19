package team._0mods.aeternus.init.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import java.util.function.BiConsumer
import java.util.function.Supplier

object AeternusRegsitry {
    val tab = ResourceKey.create(Registries.CREATIVE_MODE_TAB, resloc(ModId, "aeternus"))

    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))
}