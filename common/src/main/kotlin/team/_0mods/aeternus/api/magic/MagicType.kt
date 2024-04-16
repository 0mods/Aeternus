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
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.common.ModId

@Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
@ApiStatus.ScheduledForRemoval
interface MagicType {
    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    fun getId(): String

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    fun asLevel(): Int

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    fun getTranslation(): MutableComponent = getMagicTypeMessage("type.${this.getId()}").withStyle(*this.getStyles())

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    fun getClassifier(): MagicClassifier

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    fun getStyles(): Array<out ChatFormatting>

    companion object {
        @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
        @ApiStatus.ScheduledForRemoval
        @JvmStatic
        fun getMagicMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magic.%s.%s", ModId, message), *objects)
        }

        @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
        @ApiStatus.ScheduledForRemoval
        @JvmStatic
        fun getMagicTypeMessage(message: String, vararg objects: Any?): MutableComponent {
            return Component.translatable(String.format("magicType.%s", message), *objects)
        }
    }

    @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
    @ApiStatus.ScheduledForRemoval
    enum class MagicClassifier {
        @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
        @ApiStatus.ScheduledForRemoval
        MAIN_TYPE,
        @Deprecated("Deprecated", ReplaceWith("Not replaceable"), DeprecationLevel.WARNING)
        @ApiStatus.ScheduledForRemoval
        SUBTYPE
    }
}