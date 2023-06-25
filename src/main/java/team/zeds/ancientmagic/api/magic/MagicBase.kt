package team.zeds.ancientmagic.api.magic

import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier
import team.zeds.ancientmagic.api.mod.Constant
import java.lang.IllegalStateException

interface MagicState {
    /**
     * Getter of [MagicType]
     *
     * Have a [MagicClassifier] check of [MagicClassifier.MAIN_TYPE]
     *
     * @return accepts objects extends [MagicType] and classifier-only [MagicClassifier.MAIN_TYPE]
     */
    fun getMagicType(): MagicType

    /**
     * Getter of [MagicType]
     *
     * Have a [MagicClassifier] check of [MagicClassifier.SUBTYPE]
     */
    fun getMagicSubtype(): MagicType

    /**
     * [Int] value setter with name "mana"
     *
     * @param max is value of max mana
     */
    fun setMaxMana(max: Int)

    fun setMagicType(type: MagicType)

    fun setMagicSubtype(type: MagicType)

    fun getMaxMana(): Int

    fun getStorageMana(): Int

    fun addMana(count: Int)

    fun subMana(count: Int)

    fun save(tag: CompoundTag)

    fun load(tag: CompoundTag)
}

interface MagicType {
    fun getId(): String

    fun numerate(): Int

    fun getTranslation(): MutableComponent

    fun getClassifier(): MagicClassifier

    fun getStyles(): Array<out ChatFormatting>

    companion object {
        @JvmStatic
        fun getMagicMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magic.%s.%s", Constant.KEY, message), *objects)
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

enum class MagicTypes(
    private val id: String,
    private val num: Int,
    private val classifier: MagicClassifier,
    private vararg val style: ChatFormatting,
) : MagicType {
    LOW_MAGIC("low_magic", 0, MagicClassifier.MAIN_TYPE),
    MEDIUM_MAGIC("medium_magic", 1, MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    PRE_HIGH_MAGIC("pre_high", 2, MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    HIGH_MAGIC("high_magic", 3, MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    SUPERIOR("superior", 4, MagicClassifier.MAIN_TYPE, ChatFormatting.RED),
    GENERATING("generating", MagicClassifier.SUBTYPE),
    CONSUMING("consuming", MagicClassifier.SUBTYPE),
    STORAGE("storage", MagicClassifier.SUBTYPE),
    ADMIN("admin", MagicClassifier.SUBTYPE);

    constructor(id: String, num: Int, classifier: MagicClassifier) :
            this(id, num, classifier, ChatFormatting.WHITE)
    constructor(id: String, classifier: MagicClassifier) :
            this(id, -1, classifier, ChatFormatting.WHITE)

    override fun getId(): String = this.id
    override fun numerate(): Int = this.num
    override fun getTranslation(): MutableComponent = MagicType.getMagicTypeMessage(String.format("type.%s", getId()))
        .withStyle(*this.getStyles())
    override fun getClassifier(): MagicClassifier = this.classifier
    override fun getStyles(): Array<out ChatFormatting> = this.style

    open fun getById(name: String): MagicType {
        return if (name.isNotEmpty()) MagicTypes.valueOf(name) else LOW_MAGIC
    }

    companion object {
        @JvmStatic
        fun getByNumeration(id: Int): MagicType {
            return if (id > 0 && id <= MagicTypes.entries.size) MagicTypes.entries[id] else LOW_MAGIC
        }
        @JvmStatic
        fun create(
            id: String,
            num: Int,
            classifier: MagicClassifier,
            vararg style: ChatFormatting
        ): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
        @JvmStatic
        fun create(id: String, num: Int, classifier: MagicClassifier): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
        @JvmStatic
        fun create(id: String, classifier: MagicClassifier): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
    }
}
