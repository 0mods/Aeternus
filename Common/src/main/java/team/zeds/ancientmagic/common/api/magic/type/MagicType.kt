package team.zeds.ancientmagic.common.api.magic.type

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import team.zeds.ancientmagic.common.AMConstant

interface MagicType {
    fun getId(): String

    fun asLevel(): Int

    fun getTranslation(): MutableComponent

    fun getClassifier(): MagicClassifier

    fun getStyles(): Array<out ChatFormatting>

    companion object {
        @JvmStatic
        val listOfMagicTypes: MutableList<MagicType> = mutableListOf()
        @JvmStatic
        val listOfMagicSubtypes: MutableList<MagicType> = mutableListOf()

        @JvmStatic
        fun getMagicMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magic.%s.%s", AMConstant.KEY, message), *objects)
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