package team.zeds.aeternus.world

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation as location
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.biome.Biome
import team.zeds.aeternus.reLoc

object BiomeRegistryHolder {
    lateinit var biomeRegistry: Registry<Biome>

    @JvmStatic
    fun loadBiomeReg(server: MinecraftServer) {
        biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME)
    }

    @JvmStatic
    fun idToRL(id: Int) = if (id == -1) reLoc("a", "empty") else biomeRegistry.getHolder(id).get().key().location()

    fun rlToId(biome: location) = biomeRegistry.getId(biomeRegistry.get(biome))
}