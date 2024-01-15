package team.zeds.aeternus.init

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team.zeds.aeternus.api.item.Materials

const val ModId = "aeternus"

fun resloc(id: String, path: String) = ResourceLocation(id, path)

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const
