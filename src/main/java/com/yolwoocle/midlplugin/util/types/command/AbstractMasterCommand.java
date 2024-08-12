package com.yolwoocle.midlplugin.util.types.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMasterCommand extends AbstractCommand {

    public static class ChildCommandAlreadyRegisteredException extends RuntimeException {
        public ChildCommandAlreadyRegisteredException() { super(); }
        public ChildCommandAlreadyRegisteredException(String message) { super(message); }
        public ChildCommandAlreadyRegisteredException(String message, Throwable cause) { super(message, cause); }
        public ChildCommandAlreadyRegisteredException(Throwable cause) { super(cause); }
    }

    public static class ChildCommandNotRegisteredException extends RuntimeException {
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

//    @Override
//    protected abstract List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args, String[] labelPath);

//    @Override
//    protected abstract boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args, String[] labelPath);

    //

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();

        List<String> childResult = this.handleChildCommandTabCompletion(sender, cmd, label, args);

        if(childResult != null && !childResult.isEmpty()) result.addAll(childResult);
        else {
            List<String> selfResult = super.onTabComplete(sender, cmd, label, args);
            if (selfResult != null) result.addAll(selfResult);
        }

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
            throw new ChildCommandAlreadyRegisteredException();
        }

        this.childCommandMap.put(childCommand.label(), childCommand);
    }

    public void unregisterChildCommand(AbstractCommand childCommand) throws ChildCommandNotRegisteredException {
        if(!this.isChildCommandRegistered(childCommand)) {
            throw new ChildCommandNotRegisteredException();
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

    private List<String> handleChildCommandTabCompletion(CommandSender sender, Command cmd, String parentRawLabel, String[] parentArgs) {
        if(parentArgs.length <= 1) {
            return this.childCommandMap.values().stream().map(AbstractCommand::label).toList();
        }
        else {
            String[] childArgs = new String[parentArgs.length-1];
            for(int i = 1; i < parentArgs.length; ++i) childArgs[i-1] = parentArgs[i];

            String childLabel = parentArgs[0];
            String childRawLabel = parentRawLabel + " " + childLabel;
            return this.callChildCommandTabCompletion(sender, cmd, childRawLabel, childLabel, childArgs);
        }
    }

    public List<String> callChildCommandTabCompletion(CommandSender sender, Command cmd, String childRawLabel, String childLabel, String[] childArgs) {
        AbstractCommand childCommand = this.childCommandMap.get(childLabel);
        return childCommand != null ? childCommand.onTabComplete(sender, cmd, childRawLabel, childArgs) : null;
    }

    //

    private boolean handleChildCommand(CommandSender sender, Command cmd, String parentRawLabel, String[] parentArgs) {
        if(parentArgs.length == 0) {
            return false;
        }
        else {
            String[] childArgs = new String[parentArgs.length-1];
            System.arraycopy(parentArgs, 1, childArgs, 0, parentArgs.length - 1);

            String childLabel = parentArgs[0];
            String childRawLabel = parentRawLabel + " " + childLabel;
            return this.callChildCommand(sender, cmd, childRawLabel, childLabel, childArgs);
        }
    }

    public boolean callChildCommand(CommandSender sender, Command cmd, String childRawLabel, String childLabel, String[] childArgs) {
        AbstractCommand childCommand = this.childCommandMap.get(childLabel);
        return childCommand != null && childCommand.onCommand(sender, cmd, childRawLabel, childArgs);
    }
}
