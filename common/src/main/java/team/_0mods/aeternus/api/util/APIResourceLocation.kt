package team._0mods.aeternus.api.util

import team._0mods.aeternus.platformredirect.api.util.rl

/**
 * Mappings resolve a problem.
 * Can be used as [net.minecraft.resources.ResourceLocation]
 */
interface APIResourceLocation {
    companion object

    val rlPath: String
    val rlNamespace: String

    val asString: String
    val debugName: String
}

@Suppress("cast_never_succeeds")
fun APIResourceLocation.Companion.createRL(namespace: String, path: String): APIResourceLocation {
    return ("$namespace:$path".rl) as APIResourceLocation
}

fun APIResourceLocation.Companion.createRL(id: String): APIResourceLocation {
    return (id.rl) as APIResourceLocation
}
