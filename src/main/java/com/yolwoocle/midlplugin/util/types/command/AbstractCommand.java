package com.yolwoocle.midlplugin.util.types.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public static class SyntaxAlreadyRegisteredException extends RuntimeException {
        public SyntaxAlreadyRegisteredException() { super(); }
        public SyntaxAlreadyRegisteredException(String message) { super(message); }
        public SyntaxAlreadyRegisteredException(String message, Throwable cause) { super(message, cause); }
        public SyntaxAlreadyRegisteredException(Throwable cause) { super(cause); }
    }

    public static class SyntaxNotRegisteredException extends RuntimeException {
        public SyntaxNotRegisteredException() { super(); }
        public SyntaxNotRegisteredException(String message) { super(message); }
        public SyntaxNotRegisteredException(String message, Throwable cause) { super(message, cause); }
        public SyntaxNotRegisteredException(Throwable cause) { super(cause); }
    }

    //

    public static String[] extractLabelPath(String rawLabel) { return rawLabel.split(" "); }

    public static String extractLastLabel(String rawLabel) { return rawLabel.substring(Math.max(0, rawLabel.lastIndexOf(' '))); }

    //

    private final Set<CommandOption> options;
    private final List<CommandSyntax> syntaxes = new ArrayList<>();
    
    //    
    
    protected AbstractCommand() {
        this(Set.of());
    }

    protected AbstractCommand(Set<CommandOption> options) {
        this.options = options;
    }

    //

    public abstract String label();

    public Set<CommandOption> options() { return this.options; }

    //

    public boolean isSyntaxRegistered(CommandSyntax syntax) { return this.syntaxes.contains(syntax); }

    public AbstractCommand registerSyntax(CommandSyntax syntax) throws SyntaxAlreadyRegisteredException {
        if(this.isSyntaxRegistered(syntax)) throw new SyntaxAlreadyRegisteredException();
        this.syntaxes.add(syntax);
        return this;
    }

    public AbstractCommand unregisterSyntax(CommandSyntax syntax) throws SyntaxNotRegisteredException {
        if(!this.isSyntaxRegistered(syntax)) throw new SyntaxNotRegisteredException();
        this.syntaxes.remove(syntax);
        return this;
    }

    //

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String rawLabel, String[] args) {
        if(
            ( this.options().contains(CommandOption.PLAYER_SIDE_ONLY) && !(sender instanceof Player) )
            || ( this.options().contains(CommandOption.SERVER_SIDE_ONLY) && (sender instanceof Player) )
        ) return null;

        String label = extractLastLabel(rawLabel);
        String[] labelPath = extractLabelPath(rawLabel);

        List<String> result = new ArrayList<>();
        for(CommandSyntax syntax : this.syntaxes) {
            List<String> syntaxResult = syntax.getParameter(args.length).completer().complete(sender, cmd, label, args, labelPath);
        }

        return result;
//        return this.whenTabComplete(sender, cmd, extractLastLabel(rawLabel), args, extractLabelPath(rawLabel));
    }

//    protected abstract List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args, String[] labelPath);

    //

    public boolean onCommand(CommandSender sender, Command cmd, String rawLabel, String[] args) {
        if(this.options().contains(CommandOption.PLAYER_SIDE_ONLY) && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is player-side only.");
            return true;
        }
        else if(this.options().contains(CommandOption.SERVER_SIDE_ONLY) && (sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is server-side only.");
            return true;
        }

        String label = extractLastLabel(rawLabel);
        String[] labelPath = extractLabelPath(rawLabel);

        List<String> syntaxMessages = new ArrayList<>();
        boolean hasSuccessSyntax = false;
        for(int i = 0; i < this.syntaxes.size() && !hasSuccessSyntax; ++i) {
            CommandSyntax syntax = this.syntaxes.get(i);
            hasSuccessSyntax = syntax.handle(sender, cmd, label, args, labelPath);
            if(!hasSuccessSyntax) syntaxMessages.add(syntax.generateSyntaxMessage(labelPath));
        }

        if(!hasSuccessSyntax) {
            StringBuilder syntaxErrorMessageBuilder = new StringBuilder();

            syntaxErrorMessageBuilder.append(ChatColor.RED + "Syntax error, please use one of these :");
            for(String message : syntaxMessages) syntaxErrorMessageBuilder.append("§c - ").append(message);

            sender.sendMessage(syntaxErrorMessageBuilder.toString());
        }

        return true;
    }

//    protected abstract boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args, String[] labelPath);

}

