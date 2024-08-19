package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.utils.types.command.AbstractCommand;
import com.yolwoocle.midlplugin.utils.types.command.CommandOption;
import com.yolwoocle.midlplugin.utils.types.command.CommandSyntax;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;

public class GuildJoinCommand extends AbstractCommand {

    public GuildJoinCommand() {
        super("join", Set.of(CommandOption.PLAYER_SIDE_ONLY));

        this.addPermission("midl.guild.command.join");

        this.registerSyntax(
            new CommandSyntax()
                .setParameter(0, "guild", (sender, cmd, label, args, labelPath) -> GuildManager.getInstance().getGuilds().stream().map(Guild::getName).toList())
                .setAction((sender, cmd, label, argumentMap, labelPath) -> {
                    String guildName = argumentMap.get("guild");
                    boolean success = GuildManager.getInstance().joinGuild(guildName, (Player) sender);

                    Guild guild = GuildManager.getInstance().getGuild(guildName);

                    if(success) sender.sendMessage("You successfully joined the guild " + guild.getDisplayName() + ChatColor.RESET + ".");
                    else sender.sendMessage(ChatColor.RED + "Failed to join the guild " + guildName + ". Please verify you're not already in a guild.");

                    return true;
                })
        );
    }

}
