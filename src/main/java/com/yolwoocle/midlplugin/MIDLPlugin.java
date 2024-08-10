package com.yolwoocle.midlplugin;

import com.yolwoocle.midlplugin.command.TeamCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MIDLPlugin extends JavaPlugin {
    private static MIDLPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic
        getLogger().info("Plugin MIDL activé.");
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.sendMessage("Hello from MIDL");
        }

        Objects.requireNonNull(getCommand("team")).setExecutor(new TeamCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MIDLPlugin getInstance() {
        return instance;
    }
}
