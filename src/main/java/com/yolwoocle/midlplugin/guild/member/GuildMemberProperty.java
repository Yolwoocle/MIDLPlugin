package com.yolwoocle.midlplugin.guild.member;

import com.yolwoocle.midlplugin.guild.GuildManager;
import org.bukkit.configuration.ConfigurationSection;

public class GuildMemberProperty<T> {

    private final ConfigurationSection section;
    private final String path;
    private T value;

    public GuildMemberProperty(ConfigurationSection section, String path, T defaultValue) {
        this.section = section;
        this.path = path;

        if (!section.contains(path))
            set(defaultValue);
        this.value = (T) section.get(path);
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        this.section.set(path, value);
        GuildManager.saveConfig();
    }

    public void remove() {
        this.section.set(path, null);
        GuildManager.saveConfig();
    }

}
