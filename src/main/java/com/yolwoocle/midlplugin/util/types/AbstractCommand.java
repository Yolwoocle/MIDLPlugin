package com.yolwoocle.midlplugin.util.types;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public abstract String label();

    //

    public AbstractCommand() {

    }

    //

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return this.whenTabComplete(sender, cmd, label, args);
    }

    protected abstract List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args);

    //

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return this.whenCommand(sender, cmd, label, args);
    }

    protected abstract boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args);

}

