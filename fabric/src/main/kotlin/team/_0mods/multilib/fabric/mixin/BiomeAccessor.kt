package team._0mods.multilib.fabric.mixin

import net.minecraft.world.level.biome.Biome
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(Biome::class)
interface BiomeAccessor {
    @Accessor("climateSettings")
    fun getClimateSettings(): Biome.ClimateSettings
}