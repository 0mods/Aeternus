package team.zeds.ancientmagic.api.mod

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team.zeds.ancientmagic.capability.BlockManaCapability

object AMConstant {
    @JvmField internal val LOGGER: Logger = LoggerFactory.getLogger("AncientMagic")
    @JvmField internal val BLOCK_MANA_CAPABILITY = BlockManaCapability.Provider()
    internal const val KEY: String = "ancientmagic"
    internal const val CURIO_KEY: String = "curios"
    internal const val WAYSTONES_KEY: String = "waystones"
    @JvmStatic fun reloc(obj: String): ResourceLocation = ResourceLocation(KEY, obj)
}