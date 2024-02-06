package team._0mods.aeternus.api.magic

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import team._0mods.aeternus.ModId

interface MagicType {
    fun getId(): String

    fun asLevel(): Int

    fun getTranslation(): MutableComponent = getMagicTypeMessage("type.${this.getId()}").withStyle(*this.getStyles())

    fun getClassifier(): MagicClassifier

    fun getStyles(): Array<out ChatFormatting>

    companion object {

        @JvmStatic
        fun getMagicMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magic.%s.%s", ModId, message), *objects)
        }

        @JvmStatic
        fun getMagicTypeMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magicType.%s", message), *objects)
        }
    }

    enum class MagicClassifier {
        MAIN_TYPE, SUBTYPE
    }
}