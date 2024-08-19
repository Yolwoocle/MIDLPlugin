package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.utils.types.command.AbstractMasterCommand;
import com.yolwoocle.midlplugin.utils.types.command.CommandSyntax;
import org.bukkit.Bukkit;

import java.util.List;

public class GuildListCommand extends AbstractMasterCommand {

    public GuildListCommand() {
        super("list");

        this.registerSyntax(
            new CommandSyntax()
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    StringBuilder stringBuilder = new StringBuilder();

                    List<Guild> guilds = GuildManager.getInstance().getGuilds();

                    if (guilds.isEmpty()) {
                        sender.sendMessage("There are currently no guilds.");
                        return true;
                    }

                    stringBuilder.append("Current guilds:\n");
                    for (Guild guild: guilds) {
                        List<String> memberNames = guild.getMembers()
                            .stream()
                            .map((member) -> member.getPlayer().getName())
                            .sorted()
                            .toList();
                        stringBuilder.append(" - " + guild.getDisplayName() + " (" + memberNames.size() + " members): ");

                        if (memberNames.isEmpty()) {
                            stringBuilder.append("[No players]");
                        } else {
                            for (int i = 0; i < memberNames.size(); i++) {
                                stringBuilder.append(memberNames.get(i));
                                if (i < memberNames.size() - 1) {
                                    stringBuilder.append(", ");
                                }
                            }
                        }

                        stringBuilder.append("\n");
                    }

                    sender.sendMessage(stringBuilder.toString());

                    return true;
                })
        );
    }

}
