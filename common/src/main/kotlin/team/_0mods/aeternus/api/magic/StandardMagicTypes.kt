/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.ApiStatus

@Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
@ApiStatus.ScheduledForRemoval
enum class StandardMagicTypes: MagicType {
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    LOW_MAGIC("low_magic", 0, MagicType.MagicClassifier.MAIN_TYPE),
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    MEDIUM_MAGIC("medium_magic", 1, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    PRE_HIGH_MAGIC("pre_high", 2, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    HIGH_MAGIC("high_magic", 3, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    SUPERIOR("superior", 4, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.RED),;

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    private val id: String
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    private val num: Int
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    private val classifier: MagicType.MagicClassifier
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    private val style: Array<out ChatFormatting>

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
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
    }

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    constructor(id: String, num: Int, classifier: MagicType.MagicClassifier) : this(id, num, classifier, ChatFormatting.WHITE)
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    constructor(id: String, classifier: MagicType.MagicClassifier) : this(id, -1, classifier, ChatFormatting.WHITE)

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    override fun getId(): String = this.id
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    override fun asLevel(): Int = this.num
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    override fun getClassifier(): MagicType.MagicClassifier = this.classifier
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    override fun getStyles(): Array<out ChatFormatting> = this.style

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    open fun getById(name: String): MagicType {
        return if (name.isNotEmpty()) StandardMagicTypes.valueOf(name) else LOW_MAGIC
    }

    companion object {
        @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
        @ApiStatus.ScheduledForRemoval
        @JvmStatic
        fun create(
            id: String,
            num: Int,
            classifier: MagicType.MagicClassifier,
            vararg style: ChatFormatting
        ): MagicType = object : MagicType {
            override fun getId(): String = id

            override fun asLevel(): Int = num

            override fun getTranslation(): MutableComponent = MagicType.getMagicTypeMessage("type.${this.getId()}")

            override fun getClassifier(): MagicType.MagicClassifier = classifier

            override fun getStyles(): Array<out ChatFormatting> = style
        }
    }
}
