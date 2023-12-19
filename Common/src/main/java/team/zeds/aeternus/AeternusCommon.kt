package team.zeds.aeternus

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team.zeds.aeternus.api.item.Materials

const val ModId = "aeternus"

fun reLoc(id: String, path: String) = ResourceLocation(id, path)

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const


fun testMaterial( ) {
    val matcreate = Materials.create(ModId, "my_first_material")
        .setupProperties {

        }
        .fullSet()
        .build()
}