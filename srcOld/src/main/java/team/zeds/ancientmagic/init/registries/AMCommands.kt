package team.zeds.ancientmagic.init.registries

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.RegisterCommandsEvent
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.mod.AMConstant
import team.zeds.ancientmagic.capability.PlayerMagicCapability
import team.zeds.ancientmagic.client.render.AMShaders
import team.zeds.ancientmagic.init.config.AMCommon
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier

object AMCommands {
    var var0: Boolean = true

    @JvmField
    val NAMES_OF_COMMAND = mutableListOf(
        "am",
        AMConstant.KEY,
        "ancient",
        "magicancient"
    )

    @JvmStatic
    fun registerCommands(e: RegisterCommandsEvent) {
        commandRegister(e.dispatcher)
    }

    private fun commandRegister(sourceStack: CommandDispatcher<CommandSourceStack>) {
        val names = AMCommon.instance!!.commandsIdentifier.get()
        for (name in names) {
            sourceStack.register(
                Commands.literal(name)
                    .then(registerSetStage())
                    .then(Commands.literal("particle").executes{cmd -> particle(cmd.source)})
            )
        }
    }

    private fun registerSetStage(): ArgumentBuilder<CommandSourceStack?, *>? {
        return Commands.literal("level")
            .requires { req: CommandSourceStack -> req.hasPermission(Commands.LEVEL_ADMINS) }
            .then(
                Commands.argument("players", EntityArgument.players())
                    .then(Commands.argument("levels", IntegerArgumentType.integer(0, 4)))
            )
            .executes { cmd: CommandContext<CommandSourceStack> ->
                setLevel(
                    cmd.source, EntityArgument.getPlayers(cmd, "players"),
                    IntegerArgumentType.getInteger(cmd, "levels")
                )
            }
    }

    private fun setLevel(sourceStack: CommandSourceStack, players: Collection<ServerPlayer>, countOfLevels: Int): Int {
        val returnValue = AtomicInteger()
        for (player in players) {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap: PlayerMagicCapability ->
                val iValue = cap.magicLevel
                if (iValue == countOfLevels && countOfLevels == 4) {
                    command("max").get().let { sourceStack.sendFailure(it) }
                    returnValue.set(0)
                } else if (iValue < countOfLevels) {
                    cap.setLevel(countOfLevels)
                    sourceStack.sendSuccess(command("success", countOfLevels), false)
                    returnValue.set(players.size)
                }
            }
        }
        return returnValue.get()
    }

    private fun particle(sourceStack: CommandSourceStack): Int {
        return if (this.var0) {
            this.var0 = false
            if (sourceStack.level.isClientSide) RenderSystem.setShader { AMShaders.instance!!.improvedParticle }
            1
        } else {
            this.var0 = true
            0
        }
    }

    @JvmStatic
    private fun command(message: String, vararg objs: Any): Supplier<Component> {
        return Supplier {
            MagicType.getMagicMessage(
                String.format(
                    "level.command.%s",
                    message
                ), *objs
            )
        }
    }
}
