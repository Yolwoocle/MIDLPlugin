package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.command.guild.config.GuildConfigColorCommand;
import com.yolwoocle.midlplugin.utils.types.command.AbstractMasterCommand;

public class GuildConfigCommand extends AbstractMasterCommand {
    public GuildConfigCommand() {
        super("config");

        try {
            this.registerChildCommand(new GuildConfigColorCommand());
        } catch (ChildCommandAlreadyRegisteredException e) {

        }
    }

}
