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
        dispatcher.register {}
    }
}
