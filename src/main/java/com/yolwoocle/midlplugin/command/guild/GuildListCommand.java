package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.util.types.command.AbstractMasterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GuildListCommand extends AbstractMasterCommand {

    @Override
    public String label() {
        return "list";
    }

//    @Override
//    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
//        return null;
//    }
//
//    @Override
//    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        List<Guild> guilds = GuildManager.getInstance().getGuilds();
//
//        if (guilds.isEmpty()) {
//            sender.sendMessage("There are currently no guilds.");
//            return true;
//        }
//
//        stringBuilder.append("Current guilds:\n");
//        for (Guild guild: guilds) {
//            List<String> memberNames = guild.getMembers()
//                    .stream()
//                    .map((member) -> member.getPlayer().getName())
//                    .sorted()
//                    .toList();
//            stringBuilder.append(" - " + guild.getDisplayName() + " (" + memberNames.size() + " members): ");
//
//            if (memberNames.isEmpty()) {
//                stringBuilder.append("[No players]");
//            } else {
//                for (int i = 0; i < memberNames.size(); i++) {
//                    stringBuilder.append(memberNames.get(i));
//                    if (i < memberNames.size() - 1) {
//                        stringBuilder.append(", ");
//                    }
//                }
//            }
//
//            stringBuilder.append("\n");
//        }
//
//        sender.sendMessage(stringBuilder.toString());
//
//        return true;
//    }

}
