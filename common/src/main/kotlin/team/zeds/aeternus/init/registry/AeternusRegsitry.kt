package team.zeds.aeternus.init.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.dimension.DimensionType
import team.zeds.aeternus.api.block.BlockBuilder
import team.zeds.aeternus.init.ModId
import team.zeds.aeternus.init.resloc

object AeternusRegsitry {
    lateinit var altakeDim: ResourceKey<DimensionType>
    val decorateBlock = team.zeds.aeternus.api.block.BlockBuilder.get()
        .properties { this.strength(4F, 4F) }
        .build()

    fun init() {
        altakeDim = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))
    }
}