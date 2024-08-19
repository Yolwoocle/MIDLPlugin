package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.utils.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.utils.types.command.CommandOption;
import com.yolwoocle.midlplugin.utils.types.command.CommandSyntax;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;

public class GuildLeaveCommand extends AbstractCommand {

    public GuildLeaveCommand() {
        super("leave", Set.of(CommandOption.PLAYER_SIDE_ONLY));

        this.registerSyntax(
            new CommandSyntax()
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    boolean success = GuildManager.getInstance().leaveGuild((Player) sender);

                    if (success) sender.sendMessage(ChatColor.GREEN + "You successfully left your guild.");
                    else sender.sendMessage(ChatColor.RED + "Failed to leave your guild. Please make sure you're in a team before using this command.");

                    return true;
                })
        );
    }

}
