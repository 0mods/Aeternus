package team._0mods.multilib.fabric.mixin

import com.google.common.base.Throwables
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.context.ContextChain
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyVariable
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.CommandPerformEvent

@Mixin(Commands::class)
class CommandsMixin {
    @ModifyVariable(
        method = ["finishParsing"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/commands/Commands;validateParseResults(Lcom/mojang/brigadier/ParseResults;)V"
        ),
        argsOnly = true
    )
    private fun finishParsing(results: ParseResults<CommandSourceStack>): ParseResults<CommandSourceStack>? {
        val event = CommandPerformEvent(results, null)
        if (CommandPerformEvent.EVENT.event.act(event).isPresent) {
            if (event.throwable != null) {
                Throwables.throwIfUnchecked(event.throwable!!)
            }
            return null
        }
        return event.results
    }

    @Inject(
        method = ["finishParsing"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/commands/Commands;validateParseResults(Lcom/mojang/brigadier/ParseResults;)V"
        )],
        cancellable = true
    )
    private fun finishParsing(
        results: ParseResults<CommandSourceStack>?,
        command: String,
        stack: CommandSourceStack,
        cir: CallbackInfoReturnable<ContextChain<CommandSourceStack>?>
    ) {
        if (results == null) cir.returnValue = null
    }
}