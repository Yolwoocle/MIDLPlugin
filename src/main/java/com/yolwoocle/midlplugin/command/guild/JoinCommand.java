package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinCommand extends AbstractCommand {

    @Override
    public String label() { return "join"; }

    @Override
    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return GuildManager.getInstance().getGuilds().stream().map(Guild::getName).toList();
    }

    @Override
    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;

        if(args.length < 1) {
            sender.sendMessage("§cSyntax error : /guild join <guild>");
        }
        else {
            String guildName = args[0];
            boolean success = GuildManager.getInstance().joinGuild(guildName, player);

            if(success) sender.sendMessage("§aYou successfully joined the guild §e" + guildName + "§a.");
            else sender.sendMessage("§Failed to join the guild §e" + guildName + "§c. Please verify you're not already in a team.");
        }

        return true;
    }

}
