/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.text

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import team._0mods.aeternus.common.ModId

open class TranslationBuilder protected constructor(private var prepend: String = "", private val key: String) {
    private var arguments: Array<Any> = arrayOf()
    private var formats: Array<ChatFormatting> = arrayOf()

    private constructor(key: String): this("", key)

    fun arg(arg: Any): TranslationBuilder {
        this.arguments += arg
        return this
    }

    fun args(vararg args: Any): TranslationBuilder {
        for (arg in args) this.arguments += arg

        return this
    }

    fun format(vararg format: ChatFormatting): TranslationBuilder {
        this.formats += format
        return this
    }

    fun prepend(string: String): TranslationBuilder {
        this.prepend += string
        return this
    }

    @get:JvmName("build")
    val build: MutableComponent
        get() {
            var component: MutableComponent = Component.translatable(key, arguments)

            if (prepend.isNotEmpty()) component = Component.literal(prepend).append(component)

            if (formats.isNotEmpty()) component.withStyle()

            return component
        }

    @get:JvmName("string")
    val string: String = this.build.string

    companion object {
        @JvmField
        val SHIFT = msg("shift")
        @JvmField
        val ALT = msg("alt")
        @JvmField
        val CTRL = msg("ctrl")
        @JvmField
        val SHIFT_ALT = msg("shift_alt")
        @JvmField
        val SHIFT_CTRL = msg("shift_ctrl")
        @JvmField
        val ALT_CTRL = msg("alt_ctrl")

        fun builder(key: String): TranslationBuilder = TranslationBuilder(key)

        fun builder(prepend: String, key: String): TranslationBuilder = TranslationBuilder(prepend, key)

        fun block(key: String, modId: String = ModId): TranslationBuilder = builder(
            "block.$modId.$key"
        )

        fun item(key: String, modId: String = ModId): TranslationBuilder = builder(
            "item.$modId.$key"
        )

        fun menu(key: String, modId: String = ModId): TranslationBuilder = builder(
            "menu.$modId.$key"
        )

        fun msg(key: String, modId: String = ModId): TranslationBuilder = builder(
            "msg.$modId.$key"
        )

        fun api(key: String) = builder("api.$ModId.$key").build

        fun research(key: String, modId: String) = builder("research.$modId.$key").build
    }
}
