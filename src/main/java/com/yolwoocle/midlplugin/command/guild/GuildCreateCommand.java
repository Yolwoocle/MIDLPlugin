package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.util.types.command.CommandSyntax;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GuildCreateCommand extends AbstractCommand {

    @Override
    public String label() {
        return "create";
    }

    public GuildCreateCommand() {
        this.registerSyntax(
            new CommandSyntax()
                .setParameter(0, "guild")
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    String name = argumentMap.get("guild");
                    GuildManager.getInstance().createGuild(name);

                    sender.sendMessage(ChatColor.GREEN + "Guild " + name + " successfully created.");
                    return true;
                })
        );
    }

}
