package team._0mods.aeternus

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val ModId = "aeternus"
const val ModName = "Aeternus"

fun resloc(id: String, path: String) = ResourceLocation(id, path)

val String.rl: ResourceLocation
    get() = ResourceLocation(this)

fun String.toRL(): ResourceLocation = ResourceLocation(this)

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const

fun <T, X> Map<T, X>.revert(): Map<X, T> {
    val mutableMap = mutableMapOf<X, T>()

    val keys = this.keys.toList()

    for (key in keys) {
        val value = this[key] ?: continue
        mutableMap[value] = key
    }

    return mutableMap
}

fun List<String>.toRLList(): List<ResourceLocation> {
    val rlList = mutableListOf<ResourceLocation>()
    this.forEach { // No secure, but i fix it soon
        rlList.add(it.rl)
    }
    return rlList
}

fun <T, R> Map<T, R>.fromMapToListByList(from: List<T>): List<R> {
    TODO("Wait logic")
}
