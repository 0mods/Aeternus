package team.zeds.ancientmagic.init.registries

import net.minecraftforge.common.capabilities.*
import team.zeds.ancientmagic.capability.*

object AMCapability {
    @JvmField
    val PLAYER_MAGIC_HANDLER = CapabilityManager.get(object: CapabilityToken<PlayerMagicCapability>() {})!!
}