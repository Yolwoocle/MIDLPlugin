package com.yolwoocle.midlplugin;

import com.yolwoocle.midlplugin.command.GuildCommand;
import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.util.Configs;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class PluginMain extends JavaPlugin {

    private static PluginMain instance;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize
        GuildManager.getInstance().reloadGuilds();

        // Register configs
        Configs.register("guilds");

        // Register listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new GuildMemberListener(), this);

        // Register commands
        Objects.requireNonNull(getCommand("guild")).setExecutor(new GuildCommand());

        // Plugin startup logic
        getLogger().info("Plugin MIDL activé.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PluginMain getInstance() {
        return instance;
    }

}
