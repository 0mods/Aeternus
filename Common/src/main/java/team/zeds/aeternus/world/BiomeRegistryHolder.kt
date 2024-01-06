package team.zeds.aeternus.world

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.biome.Biome
import team.zeds.aeternus.init.resloc

object BiomeRegistryHolder {
    lateinit var biomeRegistry: Registry<Biome>

    @JvmStatic
    fun loadBiomeReg(server: MinecraftServer) {
        biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME)
    }

    @JvmStatic
    fun Int.idToRL() = (if (this == -1) resloc("a", "empty") else biomeRegistry.getHolder(this).get().key().location())!!

    fun ResourceLocation.rlToId() = biomeRegistry.getId(biomeRegistry.get(this))
}