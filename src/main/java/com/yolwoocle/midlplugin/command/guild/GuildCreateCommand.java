package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.utils.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.utils.types.command.CommandSyntax;
import org.bukkit.ChatColor;

public class GuildCreateCommand extends AbstractCommand {

    public GuildCreateCommand() {
        super("create");

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
