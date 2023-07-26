package team.zeds.ancientmagic

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AMConstant {
    internal const val MOD_NAME = "AncientMagic"
    internal const val KEY: String = "ancientmagic"
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)
    @JvmStatic fun reloc(obj: String): ResourceLocation = ResourceLocation(KEY, obj)
}