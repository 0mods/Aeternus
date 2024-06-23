package team._0mods.aeternus.api.util.mcemulate

import team._0mods.aeternus.platformredirect.api.util.mcRl

/**
 * Mapping problem resolution.
 * Can be cast as [net.minecraft.resources.ResourceLocation]
 */
interface MCResourceLocation {
    companion object

    val rlPath: String
    val rlNamespace: String

    val asString: String
    val debugName: String
}

fun MCResourceLocation.Companion.createRL(namespace: String, path: String): MCResourceLocation {
    return "$namespace:$path".mcRl
}

fun MCResourceLocation.Companion.createRL(id: String): MCResourceLocation {
    return id.mcRl
}
