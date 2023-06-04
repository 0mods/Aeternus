package team.zeromods.ancientmagic.init;

import api.ancientmagic.magic.MagicType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class AMCommands {
    public static void registerCommands(RegisterCommandsEvent e) {
        commandRegister(e.getDispatcher());
    }

    private static void commandRegister(CommandDispatcher<CommandSourceStack> sourceStack) {
        sourceStack.register(
                Commands.literal("am")
                        .then(registerSetStage())
        );
    }

    private static ArgumentBuilder<CommandSourceStack, ?> registerSetStage() {
        return Commands.literal("setStage")
                .requires(req -> req.hasPermission(Commands.LEVEL_ADMINS))
                .then(
                        Commands.argument("players", EntityArgument.players())
                                .then(Commands.argument("level", IntegerArgumentType.integer(0, 4)))
                )
                .executes(cmd -> setLevel(cmd.getSource(), EntityArgument.getPlayers(cmd, "players"),
                        IntegerArgumentType.getInteger(cmd, "levels")));
    }

    private static int setLevel(CommandSourceStack sourceStack, Collection<ServerPlayer> players, int countOfLevels) {
        AtomicInteger returnValue = new AtomicInteger();

        for (var player : players) {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
                var iValue = cap.getMagicLevel();
                if (iValue == countOfLevels && countOfLevels == 4) {
                    sourceStack.sendFailure(command("max"));
                    returnValue.set(0);
                } else if (iValue < countOfLevels) {
                    var playerData = new CompoundTag();
                    playerData.putInt("MagicPlayerData", countOfLevels);

                    cap.saveTag(playerData);

                    sourceStack.sendSuccess(command("success", countOfLevels), false);

                    returnValue.set(players.size());
                }
            });
        }
        return returnValue.get();
    }

    private static MutableComponent command(String message, Object... objs) {
        return MagicType.getMagicMessage(String.format("level.command.%s", message), objs);
    }
}
