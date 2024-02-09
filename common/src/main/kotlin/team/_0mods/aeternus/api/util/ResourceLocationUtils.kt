@file:JvmName("RLUtils")

package team._0mods.aeternus.api.util

import net.minecraft.resources.ResourceLocation

fun resloc(id: String, path: String) = ResourceLocation(id, path)

val String.rl: ResourceLocation
    get() = ResourceLocation(this)

fun String.toRL(): ResourceLocation = ResourceLocation(this)
