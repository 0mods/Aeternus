/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import kotlinx.coroutines.*
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import team._0mods.aeternus.api.commands.*
import team._0mods.aeternus.api.config.CommentedValue
import team._0mods.aeternus.api.config.regenerateCfg
import team._0mods.aeternus.api.config.prefix
import team._0mods.aeternus.api.util.mcText
import team._0mods.aeternus.api.util.mcTranslate
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.ModName
import team._0mods.aeternus.common.clientConfig
import team._0mods.aeternus.common.commonConfig
import team._0mods.aeternus.common.init.config.AeternusCommonConfig

object AeternusCommands {
    @JvmStatic
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val defaults = AeternusCommonConfig.defaultConfig
        val commandNotWorks = SimpleCommandExceptionType("command.aeternus.not_works".mcTranslate)

        dispatcher.register {
            ModId {
                "config" {
                    val mess = "command.aeternus.config.common".mcTranslate.appendSuffix
                        .appendlnCommented("command.aeternus.config.debug", commonConfig.debug, defaults.debug)
                        .appendln("command.aeternus.config.experimental")
                        .appendlnCommented("command.aeternus.config.experimental.enable", commonConfig.experimental.enableExperimentals, defaults.experimental.enableExperimentals)
                        .appendln(
                            "command.aeternus.config.experimental.butter",
                            boolMess(commonConfig.experimental.butterMechanic)
                        )

                    val client = "command.aeternus.config.client".mcTranslate.appendSuffix
                        .appendln("command.aeternus.config.low_mode", boolMess(clientConfig.lowMode))

                    val player = if (source.isPlayer) source.player else null
                    player?.sendSystemMessage(ModName.mcText.withStyle(ChatFormatting.DARK_PURPLE))
                    player?.sendSystemMessage(mess)
                    player?.sendSystemMessage(client)
                }

                "reload_config" {
                    commandNotWorks.create()
                    val player = if (source.isPlayer) source.player else null
                    player?.sendSystemMessage("command.aeternus.not_works".mcTranslate)
                }

                "regenerate_config" {
                    val regenerationScope = CoroutineScope(Dispatchers.Default)

                    regenerationScope.launch {
                        source.player?.sendSystemMessage("command.aeternus.config.regenerating".mcTranslate)
                        delay(1000)
                        regenerateCfg(AeternusCommonConfig(), prefix("common"))
                        delay(1000)
                        source.player?.sendSystemMessage("command.aeternus.config.regenerated".mcTranslate)
                        delay(100)
                        regenerationScope.cancel()
                    }
                }
            }
        }
    }

    private val MutableComponent.appendSuffix
        get() = this.appendSuffix("".mcText)

    private fun MutableComponent.appendln(mess: String, value: Component = "".mcText) = this.append("\n").append(" ").append(mess.mcTranslate).appendSuffix(value)

    private fun MutableComponent.appendSuffix(mess: Component) = this.append(":").append(" ").append(mess)

    private fun MutableComponent.appendLined(text: Component) = this.append("\n").append(" ").append(" ").append("\\").append("-").append(" ").append(text)

    private fun <T> MutableComponent.appendlnCommented(tr: String, commentedValue: CommentedValue<T>, commentFrom: CommentedValue<T>): MutableComponent {
        val err = "command.aeternus.unknown_value".mcTranslate

        val value = commentedValue() ?: appendln(tr, err)
        val clazz = value::class

        val comments = commentFrom.comment
        val commentBuilder = StringBuilder()

        for (i in comments.indices) {
            commentBuilder.append(comments[i]).append(';')

            if (i < comments.size - 1) {
                commentBuilder.append("\n").append(" ").append(" ").append(" ").append("|").append("_").append(" ")
            }
        }

        val componentedComments = commentBuilder.toString().mcText

        return when(clazz) {
            Boolean::class -> appendln(tr, boolMess(value as Boolean))
                .appendLined("command.aeternus.comment".mcTranslate).appendSuffix.appendLined(componentedComments)
            Int::class -> appendln(tr, "${value as Int}".mcText.withStyle(ChatFormatting.BLUE)
                .appendLined("command.aeternus.comment".mcTranslate).appendSuffix.appendLined(componentedComments))
            Double::class -> appendln(tr, "${value as Double}".mcTranslate
                .appendLined("command.aeternus.comment".mcTranslate).appendSuffix.appendLined(componentedComments))
            String::class -> appendln(tr, (value as String).mcText.withStyle(ChatFormatting.BLUE)
                .appendLined("command.aeternus.comment".mcTranslate).appendSuffix.appendLined(componentedComments))

            else -> appendln(tr, err)
        }
    }

    private fun boolMess(boolean: Boolean) =
        if (boolean) "command.aeternus.config.enabled".mcTranslate.withStyle(ChatFormatting.GREEN)
        else "command.aeternus.config.disabled".mcTranslate.withStyle(ChatFormatting.RED)
}
