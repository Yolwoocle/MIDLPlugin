package com.yolwoocle.midlplugin.guild;

import com.yolwoocle.midlplugin.guild.member.GuildMember;
import com.yolwoocle.midlplugin.util.Configs;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuildManager {

    private static GuildManager instance;

    private final FileConfiguration config = getConfig();
    private final HashMap<String, Guild> guilds = new HashMap<>();
    private final HashMap<UUID, GuildMember> guildMemberMap = new HashMap<>();


    public static GuildManager getInstance() {
        if (instance == null) {
            instance = new GuildManager();
        }
        return instance;
    }

    private GuildManager() {
    }

    public void reloadGuilds() {
        this.guilds.clear();
        this.guildMemberMap.clear();

        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null) continue;
            Guild guild = new Guild(section);
            this.guilds.put(key, guild);
            guild.getMembers().forEach(member -> this.guildMemberMap.put(member.getPlayer().getUniqueId(), member));
        }
    }

    public void createGuild(String name, Color color) {
        this.guilds.put(name, new Guild(name, color));
    }

    public void deleteGuild(String name) {
        this.guilds.remove(name);
        config.set(name, null);
    }

    public List<Guild> getGuilds() {
        return List.copyOf(this.guilds.values());
    }

    public boolean joinGuild(String name, OfflinePlayer player) {
        if (this.hasGuild(player)) return false;

        GuildMember guildMember = this.guilds.get(name).addPlayer(player);
        this.guildMemberMap.putIfAbsent(player.getUniqueId(), guildMember);

        return true;
    }

    public boolean leaveGuild(OfflinePlayer player) {
        GuildMember member = getMember(player);
        if (member == null) return false;

        member.getGuild().removePlayer(player);
        this.guildMemberMap.remove(player.getUniqueId());

        return true;
    }

    public Guild getGuild(String name) {
        return this.guilds.get(name);
    }

    public Guild getGuild(OfflinePlayer player) {
        return this.guildMemberMap.get(player.getUniqueId()).getGuild();
    }

    public boolean hasGuild(OfflinePlayer player) {
        return this.getGuild(player) != null;
    }

    public Guild getGuild(Location location) {
        for (Guild guild : this.guilds.values()) {
            if (guild.getBoundingBox().contains(location.toVector())) {
                return guild;
            }
        }
        return null;
    }

    public GuildMember getMember(OfflinePlayer player) {
        return this.guildMemberMap.get(player.getUniqueId());
    }

    public static void saveConfig() {
        Configs.save("guilds");
    }

    public static FileConfiguration getConfig() {
        return Configs.getConfig("guilds");
    }

}
