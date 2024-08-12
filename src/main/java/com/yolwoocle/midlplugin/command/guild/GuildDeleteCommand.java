package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.util.types.command.CommandOption;
import com.yolwoocle.midlplugin.util.types.command.CommandSyntax;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class GuildDeleteCommand extends AbstractCommand {

    @Override
    public String label() {
        return "delete";
    }

    public GuildDeleteCommand() {
//        super(Set.of(CommandOption.PLAYER_SIDE_ONLY));

        this.registerSyntax(
            new CommandSyntax()
                .setParameter(0, "guild", (sender, cmd, label, args, labelPath) -> GuildManager.getInstance().getGuilds().stream().map(Guild::getName).toList())
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    String guildName = argumentMap.get("guild");
                    Guild guild = GuildManager.getInstance().getGuild(guildName);

                    if (guild == null) {
                        sender.sendMessage(ChatColor.RED + "This guild doesn't exist.");
                        return true;
                    }

                    String guildDisplayName = guild.getDisplayName();
                    boolean success = GuildManager.getInstance().deleteGuild(guildName);

                    if (success) sender.sendMessage("The guild " + guildDisplayName + " was deleted.");
                    else sender.sendMessage(ChatColor.RED + "Error while deleting the guild.");

                    return true;
                })
        );
    }

}
