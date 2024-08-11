package com.yolwoocle.midlplugin.util.types;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMasterCommand extends AbstractCommand {

    public static class ChildCommandAlreadyRegisteredException extends Exception {
        public ChildCommandAlreadyRegisteredException() { super(); }
        public ChildCommandAlreadyRegisteredException(String message) { super(message); }
        public ChildCommandAlreadyRegisteredException(String message, Throwable cause) { super(message, cause); }
        public ChildCommandAlreadyRegisteredException(Throwable cause) { super(cause); }
    }

    public static class ChildCommandNotRegisteredException extends Exception {
        public ChildCommandNotRegisteredException() { super(); }
        public ChildCommandNotRegisteredException(String message) { super(message); }
        public ChildCommandNotRegisteredException(String message, Throwable cause) { super(message, cause); }
        public ChildCommandNotRegisteredException(Throwable cause) { super(cause); }
    }

    //

    private Map<String, AbstractCommand> childCommandMap = new HashMap<>();

    //

    @Override
    public abstract String label();

    @Override
    protected abstract List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    protected abstract boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args);

    //


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();

        List<String> childResult = this.handleChildCommandTabCompletion(sender, cmd, label, args);
        if(childResult != null) result.addAll(childResult);

        List<String> selfResult = super.onTabComplete(sender, cmd, label, args);
        if(selfResult != null) result.addAll(selfResult);

        return result;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!this.handleChildCommand(sender, cmd, label, args)) {
            return super.onCommand(sender, cmd, label, args);
        }

        return true;
    }

    //

    public void registerChildCommand(AbstractCommand childCommand) throws ChildCommandAlreadyRegisteredException {
        if(this.isChildCommandRegistered(childCommand)) {
            throw new AbstractMasterCommand.ChildCommandAlreadyRegisteredException();
        }

        this.childCommandMap.put(childCommand.label(), childCommand);
    }

    public void unregisterChildCommand(AbstractCommand childCommand) throws ChildCommandNotRegisteredException {
        if(!this.isChildCommandRegistered(childCommand)) {
            throw new AbstractMasterCommand.ChildCommandNotRegisteredException();
        }

        this.childCommandMap.remove(childCommand.label());
    }

    public boolean isChildCommandRegistered(AbstractCommand childCommand) {
        return this.childCommandMap.containsKey(childCommand.label());
    }

    public AbstractCommand getRegisteredChildCommandByLabel(String label) {
        return this.childCommandMap.get(label);
    }

    //

    private List<String> handleChildCommandTabCompletion(CommandSender sender, Command cmd, String parentLabel, String[] parentArgs) {
        if(parentArgs.length == 0) {
            List<String> result = new ArrayList<>();

            for(AbstractCommand childCommand : this.childCommandMap.values()) {
                result.add(childCommand.label());
            }

            return  result;
        }
        else {
            String[] childArgs = new String[parentArgs.length-1];
            for(int i = 1; i < parentArgs.length; ++i) childArgs[i-1] = parentArgs[i];

            return this.onTabComplete(sender, cmd, parentArgs[0], childArgs);
        }
    }

    public List<String> callChildCommandTabCompletion(CommandSender sender, Command cmd, String childLabel, String[] childArgs) {
        AbstractCommand childCommand = this.childCommandMap.get(childLabel);
        if(childCommand != null) {
            return childCommand.onTabComplete(sender, cmd, childLabel, childArgs);
        }
        else return null;
    }

    //

    private boolean handleChildCommand(CommandSender sender, Command cmd, String parentLabel, String[] parentArgs) {
        if(parentArgs.length == 0) {
            return false;
        }
        else {
            String[] childArgs = new String[parentArgs.length-1];
            for(int i = 1; i < parentArgs.length; ++i) childArgs[i-1] = parentArgs[i];

            return this.callChildCommand(sender, cmd, parentArgs[0], childArgs);
        }
    }

    public boolean callChildCommand(CommandSender sender, Command cmd, String childLabel, String[] childArgs) {
        AbstractCommand childCommand = this.childCommandMap.get(childLabel);
        if(childCommand != null) {
            return childCommand.onCommand(sender, cmd, childLabel, childArgs);
        }
        else return false;
    }
}
