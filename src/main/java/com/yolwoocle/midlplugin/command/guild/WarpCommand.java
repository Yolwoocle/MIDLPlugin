package com.yolwoocle.midlplugin.command.guild;

import com.yolwoocle.midlplugin.util.types.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WarpCommand extends AbstractCommand {

    @Override
    public String label() { return "warp"; }

    @Override
    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
        

        return true;
    }
}
