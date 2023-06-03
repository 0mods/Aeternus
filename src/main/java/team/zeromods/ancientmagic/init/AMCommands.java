package team.zeromods.ancientmagic.init;

import api.ancientmagic.magic.MagicType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import team.zeromods.ancientmagic.capability.AMCapability;

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
                        var playerData = cap.getTag();
                        if (playerData.get("MagicPlayerData") != null) {
                            var iValue = playerData.getInt("MagicPlayerData");
                            if (iValue == countOfLevels && countOfLevels == 4) {
                                sourceStack.sendFailure(command("max"));

                                returnValue.set(0);
                            } else if (iValue < countOfLevels) {
                                playerData.putInt("MagicPlayerData", countOfLevels);
                                sourceStack.sendSuccess(command("success", countOfLevels), false);

                                returnValue.set(players.size());
                            }
                        } else {
                            var tag = cap.getTag();

                            if (countOfLevels <= 4) {
                                tag.putInt("MagicPlayerData", countOfLevels);
                                sourceStack.sendSuccess(command("success", countOfLevels), false);
                            } else if (countOfLevels > 4) {
                                sourceStack.sendFailure(command("numberIsLarge"));

                                returnValue.set(0);
                            }
                        }
                    }
            );
        }
        return returnValue.get();
    }

    private static MutableComponent command(String message, Object... objs) {
        return MagicType.getMagicMessage(String.format("level.command.%s", message), objs);
    }
}
