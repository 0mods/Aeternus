package team._0mods.aeternus.forge.service

import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.util.thread.SidedThreadGroups
import team._0mods.aeternus.service.core.PlatformHelper
import kotlin.jvm.optionals.getOrNull

class ForgePlatformHelper: PlatformHelper {
    override fun isProduction(): Boolean = FMLEnvironment.production

    override fun isLogicalClient(): Boolean = Thread.currentThread().threadGroup != SidedThreadGroups.SERVER

    override fun isPhysicalClient(): Boolean = FMLEnvironment.dist.isClient

    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)

    override fun getModNameByModId(modId: String): String {
        // yeah, it's very impractical, but sorry, I really want to do it >_<
        val cat = StringBuilder()
        cat.append("\n")
        cat.append("　　　　　／＞　　フ").append("\n")
        cat.append("　　　　　| 　_　 _ l").append("\n")
        cat.append("　 　　　／` ミ＿xノ").append("\n")
        cat.append("　　 　 /　　　 　 |").append("\n")
        cat.append("　　　 /　 ヽ　　 ﾉ").append("\n")
        cat.append("　 　 │　　| | |")
        val builtCat = cat.toString()
        val failedName = "Mod Name for Mod ID: $modId is not loaded! It is so sad :($builtCat"

        return ModList.get().getModContainerById(modId).getOrNull()?.modId ?: failedName
    }

    override fun isForge(): Boolean = true
}