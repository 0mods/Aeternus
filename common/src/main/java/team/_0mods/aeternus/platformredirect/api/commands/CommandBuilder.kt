/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.api.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

class CommandBuilder(private val dispatcher: CommandDispatcher<CommandSourceStack>) {
    operator fun String.invoke(operation: CommandEditor.() -> Unit) {
        val command = Commands.literal(this)
        CommandEditor(command).operation()
        dispatcher.register(command)
    }
}

class CommandEditor(private val srcCommand: LiteralArgumentBuilder<CommandSourceStack>) {
    operator fun String.invoke(
        vararg args: RequiredArgumentBuilder<CommandSourceStack, *>,
        operation: CommandContext<CommandSourceStack>.() -> Unit,
    ): LiteralArgumentBuilder<CommandSourceStack> {
        return if (args.isNotEmpty()) srcCommand.then(
            Commands.literal(this).then(operation, *args)
                .executes { ctx: CommandContext<CommandSourceStack> -> operation(ctx); 1 })
        else srcCommand.then(
            Commands.literal(this).executes { ctx: CommandContext<CommandSourceStack> -> operation(ctx); 1 })
    }
}

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.then(
    data: CommandContext<S>.() -> Unit,
    vararg argument: ArgumentBuilder<S, *>,
): T {
    return if (argument.size > 1) this.then(
        argument[0].then(
            data,
            *argument.copyOfRange(1, argument.size)
        )
    ) else this.then(
        argument[0].executes { data(it); 1 } as ArgumentBuilder<S, *>
    )
}

fun <T> arg(name: String, type: ArgumentType<T>): RequiredArgumentBuilder<CommandSourceStack, T> =
    Commands.argument(name, type)

@JvmName("argString")
fun <T> arg(
    name: String,
    type: ArgumentType<T>,
    suggests: Collection<String>,
): RequiredArgumentBuilder<CommandSourceStack, T> =
    Commands.argument(name, type).suggests { _, builder ->
        suggests.forEach(builder::suggest)
        builder.buildFuture()
    }

@JvmName("argInt")
fun <T> arg(name: String, type: ArgumentType<T>, suggests: Collection<Int>): RequiredArgumentBuilder<CommandSourceStack, T> =
    Commands.argument(name, type).suggests { _, builder ->
        suggests.forEach(builder::suggest)
        builder.buildFuture()
    }

fun CommandDispatcher<CommandSourceStack>.register(builder: CommandBuilder.() -> Unit) {
    builder(CommandBuilder(this))
}
