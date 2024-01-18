package team._0mods.aeternus.init

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val ModId = "aeternus"

fun resloc(id: String, path: String) = ResourceLocation(id, path)

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const
