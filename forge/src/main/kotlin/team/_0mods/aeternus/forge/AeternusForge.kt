package team._0mods.aeternus.forge

import net.minecraftforge.fml.common.Mod
import team._0mods.aeternus.ModId
import team._0mods.aeternus.forge.init.AFRegistryHandler
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(ModId)
class AeternusForge {
    init {
        AFRegistryHandler.init(MOD_BUS)
    }
}
