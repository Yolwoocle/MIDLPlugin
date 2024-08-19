package com.yolwoocle.midlplugin.command.guild.config;

import com.yolwoocle.midlplugin.utils.types.command.AbstractCommand;
import org.bukkit.Color;

import java.util.HashMap;

public class GuildConfigColorCommand extends AbstractCommand {
    private HashMap<String, Color> defaultColors = new HashMap<>();

    public GuildConfigColorCommand() {
        super("color");

        this.defaultColors.put("white", Color.WHITE);
        this.defaultColors.put("silver", Color.SILVER);
        this.defaultColors.put("gray", Color.GRAY);
        this.defaultColors.put("black", Color.BLACK);
        this.defaultColors.put("red", Color.RED);
        this.defaultColors.put("maroon", Color.MAROON);
        this.defaultColors.put("yellow", Color.YELLOW);
        this.defaultColors.put("olive", Color.OLIVE);
        this.defaultColors.put("lime", Color.LIME);
        this.defaultColors.put("green", Color.GREEN);
        this.defaultColors.put("aqua", Color.AQUA);
        this.defaultColors.put("teal", Color.TEAL);
        this.defaultColors.put("blue", Color.BLUE);
        this.defaultColors.put("navy", Color.NAVY);
        this.defaultColors.put("fuchsia", Color.FUCHSIA);
        this.defaultColors.put("purple", Color.PURPLE);
        this.defaultColors.put("orange", Color.ORANGE);
    }

//    @Override
//    protected List<String> whenTabComplete(CommandSender sender, Command cmd, String label, String[] args, String[] labelPath) {
//        return this.defaultColors.keySet().stream().toList();
//    }

//    @Override
//    protected boolean whenCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        // TODO
//        if (args.length < 2) {
//            sender.sendMessage(ChatColor.RED + "Invalid format: /guild config color <color | hexColor>");
//            return true;
//        }
//        Guild guild = GuildManager.getInstance().getGuild(args[0]);
//
//        if (guild == null) {
//            sender.sendMessage(ChatColor.RED + "This guild doesn't exist.");
//            return true;
//        }
//
//        String colorText = args[1];
//        Color color;
//        if (defaultColors.containsKey(colorText)) {
//            color = defaultColors.get(colorText);
//        } else {
//            try {
//                color = ChatUtil.parseColor(colorText);
//            } catch (Exception e) {
//                sender.sendMessage(ChatColor.RED + "Invalid color format. Format: /guild config color #rrggbb");
//                return true;
//            }
//        }
//
//        if (color == null) {
//            sender.sendMessage(ChatColor.RED + "Invalid color.");
//            return true;
//        }
//
//        guild.setColor(color);
//        return true;
//    }

}
