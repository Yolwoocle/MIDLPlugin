package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveCommand extends AbstractCommand {

    @Override
    public String label() { return "leave"; }

    @Override
    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;

        if(args.length != 0) {
            sender.sendMessage("§cSyntax error : /guild leave");
        }
        else {
            boolean success = GuildManager.getInstance().leaveGuild(player);

            if(success) sender.sendMessage("§aYou successfully leaved your guild.");
            else sender.sendMessage("§Failed to leave your guild.");
        }

        return true;
    }

}
