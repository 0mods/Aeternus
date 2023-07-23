package team.zeds.ancientmagic.api.magic

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull
import team.zeds.ancientmagic.api.mod.AMConstant

interface MagicType {
    @NotNull
    fun getId(): String

    @NotNull
    fun asLevel(): Int

    @NotNull
    fun getTranslation(): MutableComponent

    @NotNull
    fun getClassifier(): MagicClassifier

    @NotNull
    fun getStyles(): Array<out ChatFormatting>

    companion object {
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