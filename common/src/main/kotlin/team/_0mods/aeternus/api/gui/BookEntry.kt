package team._0mods.aeternus.api.gui

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import net.minecraft.client.Minecraft
import net.minecraft.util.GsonHelper
import org.apache.commons.io.IOUtils
import team._0mods.aeternus.api.gui.widget.BookWidget
import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.ModId
import team._0mods.aeternus.api.util.toRL
import java.io.IOException
import java.io.Reader
import java.lang.reflect.Type
import java.util.regex.Pattern

val bookDirectory = "$ModId:book/"

class BookEntry(
    @Expose val translateName: String?,
    @Expose val parent: String?,
    @Expose val fileToReadFrom: String?,
    @Expose val requiredProgress: String?,
    @Expose val widgets: Array<BookWidget>?
) {
    private var entryText: List<String> = arrayListOf()
    private var bookLinks: List<BookLink> = arrayListOf()
    private var pageCount = 0

    companion object {
        val gson = GsonBuilder().registerTypeAdapter(BookEntry::class.java, Deserializer()).excludeFieldsWithoutExposeAnnotation().create()
        private val pattern = Pattern.compile("\\{.*?}")

        fun deserialize(reader: Reader): BookEntry? = GsonHelper.fromJson(gson, reader, BookEntry::class.java)
    }

    fun init(screen: AeternusBookScreen) {}

    private fun rawTxtFromFile(file: String, screen: AeternusBookScreen, maxLineSize: Int) {
        val lang: String = Minecraft.getInstance().languageManager.selected.lowercase()
        var fileRL = "$bookDirectory$lang/$file".toRL()
        try {
            val str = Minecraft.getInstance().resourceManager.open(fileRL)
            str.close()
        } catch (e: Exception) {
            LOGGER.warn("Failed to find language file for current ({}) translation. Using default \"en_us\"", fileRL.toString(), e)
            fileRL = "${bookDirectory}en_us/$file".toRL()
        }

        val strings = arrayListOf<String>()
        val font = Minecraft.getInstance().font
        try {
            val br = Minecraft.getInstance().resourceManager.openAsReader(fileRL)
            val readIn = IOUtils.readLines(br)
            val currentLineCount = 0
            for (rString in readIn) {
                val m = pattern.matcher(rString)
                while (m.find()) {
                    val found = m.group().split("\\|")
                    if (found.isNotEmpty()) {
                        val linkTo = found[1].substring(0, found[1].length - 1)
                        val enabled = screen.isVisibleEntry(linkTo)
                    }
                }
            }
        } catch (e: IOException) {}
    }

    fun isUnlocked(screen: AeternusBookScreen) = true

    class Deserializer: JsonDeserializer<BookEntry> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BookEntry {
            TODO("Not yet implemented")
        }
    }
}