package team._0mods.aeternus.neo.service

import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLEnvironment
import net.neoforged.fml.util.thread.SidedThreadGroups
import team._0mods.aeternus.service.core.IPlatformHelper

class PlatformHelper: IPlatformHelper {
    override fun isProduction(): Boolean = FMLEnvironment.production

    override fun isLogicalClient(): Boolean = Thread.currentThread().threadGroup != SidedThreadGroups.SERVER

    override fun isPhysicalClient(): Boolean = FMLEnvironment.dist.isClient

    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)
}