package com.yolwoocle.midlplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        if (args.length < 2) {
            player.sendMessage("Nombre d'arguments invalide");
        }

        if (args[1].equals("join")) {

        }

        player.sendMessage("HELLO");
        return true;
    }
}
