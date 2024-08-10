package com.yolwoocle.midlplugin;

import com.yolwoocle.midlplugin.command.GuildCommand;
import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.util.Configs;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class MIDLPlugin extends JavaPlugin {
    private static MIDLPlugin instance;
    private final HashMap<String, Guild> guilds = new HashMap<>();
    private final BoardManager boardManager = new BoardManager();

    @Override
    public void onEnable() {
        instance = this;


        // Register configs
        Configs.register("guilds");

        // Plugin startup logic
        getLogger().info("Plugin MIDL activé.");
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.sendMessage("Hello from MIDL");
        }

        Objects.requireNonNull(getCommand("guild")).setExecutor(new GuildCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MIDLPlugin getInstance() {
        return instance;
    }
}
