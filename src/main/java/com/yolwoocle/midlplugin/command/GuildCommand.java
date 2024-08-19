package com.yolwoocle.midlplugin.command;

import com.yolwoocle.midlplugin.command.guild.*;
import com.yolwoocle.midlplugin.utils.types.command.AbstractMasterCommand;

public class GuildCommand extends AbstractMasterCommand {

    public GuildCommand() {
        super("guild");

        try {
            this.registerChildCommand(new GuildJoinCommand());
            this.registerChildCommand(new GuildLeaveCommand());
            this.registerChildCommand(new GuildCreateCommand());
            this.registerChildCommand(new GuildDeleteCommand());
            this.registerChildCommand(new GuildListCommand());
            this.registerChildCommand(new GuildConfigCommand());
        } catch (ChildCommandAlreadyRegisteredException e) {

        }
    }
}
