package com.yolwoocle.midlplugin.guild;

import com.yolwoocle.midlplugin.util.Configs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class GuildManager {

    private static GuildManager instance;

    private HashMap<String, Guild> guilds = new HashMap<>();

    public static GuildManager getInstance() {
        if (instance == null) {
            instance = new GuildManager();
        }
        return instance;
    }

    private GuildManager() {

    }

    public void loadGuilds() {
        FileConfiguration config = Configs.getConfig("guilds");
        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null) continue;
            Guild guild = new Guild(section);
            guilds.put(key, guild);
        }
    }

    public void createGuild(String name) {
    }

    public void deleteGuild(String name) {
    }

    public void joinGuild(String name) {
    }

    public void leaveGuild(String name) {
    }

}
