package com.yolwoocle.midlplugin.command;

import com.yolwoocle.midlplugin.command.guild.JoinCommand;
import com.yolwoocle.midlplugin.command.guild.LeaveCommand;
import com.yolwoocle.midlplugin.util.types.AbstractCommand;
import com.yolwoocle.midlplugin.util.types.AbstractMasterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildCommand extends AbstractMasterCommand {

    private List<AbstractCommand> subCommands = new ArrayList<>();

    public String label() { return "guild"; }

    //

    public GuildCommand() {
        try {
            this.registerChildCommand(new JoinCommand());
            this.registerChildCommand(new LeaveCommand());
//            this.registerChildCommand(new ConfigCommand());
        } catch(ChildCommandAlreadyRegisteredException e) {

        }
    }

    //

    @Override
    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    protected boolean whenCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Syntax error : /guild [join|leave|get]");

        return true;
    }
}
