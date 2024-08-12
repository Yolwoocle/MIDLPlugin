package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.command.guild.config.GuildConfigColorCommand;
import com.yolwoocle.midlplugin.util.types.command.AbstractMasterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GuildConfigCommand extends AbstractMasterCommand {
    public GuildConfigCommand() {
        try {
            this.registerChildCommand(new GuildConfigColorCommand());
        } catch (ChildCommandAlreadyRegisteredException e) {

        }
    }

    @Override
    public String label() { return "config"; }

//    @Override
//    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        return false;
//    }

}
