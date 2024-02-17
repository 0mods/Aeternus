package team._0mods.aeternus.api.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import team._0mods.aeternus.api.util.toRL
import java.io.IOException
import java.nio.file.AccessDeniedException
import java.util.*

open class AeternusBookScreen(text: Component) : Screen(text) {
    protected fun readBookEntry(rl: ResourceLocation): BookEntry? {
        var resource: Optional<Resource>? = null
        var page: BookEntry? = null

        try {
            resource = Minecraft.getInstance().resourceManager.getResource(rl)
            if (resource.isPresent) {
                val str = resource.get().openAsReader()
                page = BookEntry.deserialize(str)
            }
        } catch (e: IOException) {
            if (e !is AccessDeniedException) {
                e.printStackTrace()
            }
        }

        return page
    }

    fun isVisibleEntry(lTo: String): Boolean {
        val rl = "${bookDirectory}$lTo".toRL()
        val entry = readBookEntry(rl)
        return entry != null && entry.isUnlocked(this) || Minecraft.getInstance().player != null && Minecraft.getInstance().player!!.isCreative
    }
}