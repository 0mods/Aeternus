package team.zeds.ancientmagic.common.api.magic.type

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent
import java.lang.IllegalStateException

enum class MagicTypes : MagicType {
    LOW_MAGIC("low_magic", 0, MagicType.MagicClassifier.MAIN_TYPE),
    MEDIUM_MAGIC("medium_magic", 1, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    PRE_HIGH_MAGIC("pre_high", 2, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    HIGH_MAGIC("high_magic", 3, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    SUPERIOR("superior", 4, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.RED),
    NOTHING("nothing", MagicType.MagicClassifier.SUBTYPE),
    GENERATING("generating", MagicType.MagicClassifier.SUBTYPE),
    CONSUMING("consuming", MagicType.MagicClassifier.SUBTYPE),
    STORAGE("storage", MagicType.MagicClassifier.SUBTYPE),
    ADMIN("admin", MagicType.MagicClassifier.SUBTYPE);

    private val id: String;
    private val num: Int;
    private val classifier: MagicType.MagicClassifier;
    private val style: Array<out ChatFormatting>

    constructor(
        id: String,
        num: Int,
        classifier: MagicType.MagicClassifier,
        vararg style: ChatFormatting,
    ) {
        this.id = id
        this.num = num
        this.classifier = classifier
        this.style = style

        when(classifier) {
            MagicType.MagicClassifier.MAIN_TYPE -> MagicType.listOfMagicTypes.add(this)
            MagicType.MagicClassifier.SUBTYPE -> MagicType.listOfMagicSubtypes.add(this)
        }
    }

    constructor(id: String, num: Int, classifier: MagicType.MagicClassifier) : this(id, num, classifier, ChatFormatting.WHITE)
    constructor(id: String, classifier: MagicType.MagicClassifier) :
            this(id, -1, classifier, ChatFormatting.WHITE)

    override fun getId(): String = this.id
    override fun asLevel(): Int = this.num
    override fun getTranslation(): MutableComponent = MagicType.getMagicTypeMessage(String.format("type.%s", getId()))
        .withStyle(*this.getStyles())
    override fun getClassifier(): MagicType.MagicClassifier = this.classifier
    override fun getStyles(): Array<out ChatFormatting> = this.style

    open fun getById(name: String): MagicType {
        return if (name.isNotEmpty()) MagicTypes.valueOf(name) else LOW_MAGIC
    }

    companion object {
        @JvmStatic
        fun getByNumeration(id: Int): MagicType {
            return if (id > 0 && id <= MagicType.listOfMagicTypes.size) MagicType.listOfMagicTypes[id] else LOW_MAGIC
        }
        @JvmStatic
        fun create(
            id: String,
            num: Int,
            classifier: MagicType.MagicClassifier,
            vararg style: ChatFormatting
        ): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
        @JvmStatic
        fun create(id: String, num: Int, classifier: MagicType.MagicClassifier): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
        @JvmStatic
        fun create(id: String, classifier: MagicType.MagicClassifier): MagicTypes {
            throw IllegalStateException("Enum not extended")
        }
    }
}