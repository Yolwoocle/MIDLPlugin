package com.yolwoocle.midlplugin;

import com.yolwoocle.midlplugin.command.GuildCommand;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.listener.GuildMemberListener;
import com.yolwoocle.midlplugin.utils.Configs;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MIDLPlugin extends JavaPlugin {

    private static MIDLPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register configs
        Configs.register("guilds");
        Configs.register("game");
        Configs.reload();

        // Initialize
        GuildManager.getInstance().reloadGuilds();

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

    public static MIDLPlugin getInstance() {
        return instance;
    }

}
