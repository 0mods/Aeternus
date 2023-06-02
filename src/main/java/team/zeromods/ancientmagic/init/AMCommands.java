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
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import team.zeromods.ancientmagic.event.forge.MagicData;

import java.util.Collection;

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
        for (var player : players) {
            var playerData = MagicData.getMagicData(player);
            if (playerData.get("MagicPlayerData") != null) {
                var iValue = playerData.getInt("MagicPlayerData");
                if (iValue == countOfLevels && countOfLevels == 4) {
                    sourceStack.sendFailure(command("max"));
                    return 0;
                } else if (iValue < countOfLevels) {
                    playerData.putInt("MagicPlayerData", countOfLevels);
                    sourceStack.sendSuccess(command("success", countOfLevels), false);
                    return players.size();
                }
            } else {
                var tag = new CompoundTag();

                if (countOfLevels <= 4) {
                    tag.putInt("MagicPlayerData", countOfLevels);
                    MagicData.setMagicData(tag, player);
                    sourceStack.sendSuccess(command("success", countOfLevels), false);
                    return players.size();
                } else if (countOfLevels > 4) {
                    sourceStack.sendFailure(command("numberIsLarge"));
                    return 0;
                }

                return 0;
            }
        }

        return 0;
    }

    private static MutableComponent command(String message, Object... objs) {
        return MagicType.getMagicMessage(String.format("level.command.%s", message), objs);
    }
}
