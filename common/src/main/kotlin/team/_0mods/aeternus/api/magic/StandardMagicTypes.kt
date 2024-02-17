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

enum class StandardMagicTypes: MagicType {
    LOW_MAGIC("low_magic", 0, MagicType.MagicClassifier.MAIN_TYPE),
    MEDIUM_MAGIC("medium_magic", 1, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    PRE_HIGH_MAGIC("pre_high", 2, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    HIGH_MAGIC("high_magic", 3, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    SUPERIOR("superior", 4, MagicType.MagicClassifier.MAIN_TYPE, ChatFormatting.RED),;

    private val id: String
    private val num: Int
    private val classifier: MagicType.MagicClassifier
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
    }

    constructor(id: String, num: Int, classifier: MagicType.MagicClassifier) : this(id, num, classifier, ChatFormatting.WHITE)
    constructor(id: String, classifier: MagicType.MagicClassifier) : this(id, -1, classifier, ChatFormatting.WHITE)

    override fun getId(): String = this.id
    override fun asLevel(): Int = this.num
    override fun getClassifier(): MagicType.MagicClassifier = this.classifier
    override fun getStyles(): Array<out ChatFormatting> = this.style

    open fun getById(name: String): MagicType {
        return if (name.isNotEmpty()) StandardMagicTypes.valueOf(name) else LOW_MAGIC
    }

    companion object {
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
