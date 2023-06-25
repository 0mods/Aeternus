package team.zeds.ancientmagic.init

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.capability.MagicObjectCapability
import team.zeds.ancientmagic.capability.PlayerMagicCapability

object AMCapability {
    @JvmField
    val PLAYER_MAGIC_HANDLER: Capability<PlayerMagicCapability> = capabilitySetter()
    @JvmField
    val MAGIC_OBJECT: Capability<MagicObjectCapability> = capabilitySetter()
    @JvmField
    val PLAYER_MAGIC_HANDLER_ID = cl("player_magic")
    @JvmField
    val MAGIC_OBJECT_ID = cl("magic_obj")

    private fun cl(name: String): ResourceLocation {
        return ResourceLocation(Constant.KEY, name)
    }

    @JvmStatic
    fun <T> capabilitySetter() : Capability<T> = CapabilityManager.get(object : CapabilityToken<T>() {})
}