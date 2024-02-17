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
import team._0mods.aeternus.common.ModId

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