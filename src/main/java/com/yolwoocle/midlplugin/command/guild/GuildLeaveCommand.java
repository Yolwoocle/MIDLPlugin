package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.util.types.command.CommandOption;
import com.yolwoocle.midlplugin.util.types.command.CommandSyntax;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class GuildLeaveCommand extends AbstractCommand {

    @Override
    public String label() { return "leave"; }

    public GuildLeaveCommand() {
        super(Set.of(CommandOption.PLAYER_SIDE_ONLY));

        this.registerSyntax(
            new CommandSyntax()
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    boolean success = GuildManager.getInstance().leaveGuild((Player) sender);

                    if (success) sender.sendMessage(ChatColor.GREEN + "You successfully left your guild.");
                    else sender.sendMessage(ChatColor.RED + "Failed to leave your guild.");

                    return true;
                })
        );
    }

//    @Override
//    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
//    }

}
