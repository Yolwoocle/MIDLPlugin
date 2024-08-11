package com.yolwoocle.midlplugin.guild;

import com.yolwoocle.midlplugin.guild.member.GuildMember;
import com.yolwoocle.midlplugin.util.Configs;
import com.yolwoocle.midlplugin.util.ConfigurationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class Guild {
    private final ConfigurationSection section;
    private HashMap<UUID, GuildMember> members;

    public Guild(ConfigurationSection section) {
        this.section = section;
        this.name = section.getName();
        this.boundingBox = ConfigurationUtil.getBoundingBox(section.getConfigurationSection("bounding-box"));
        this.color = ConfigurationUtil.getColor(section, "color");
        this.team = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(this.name);

        if (boundingBox == null) {
            throw new IllegalArgumentException("Bounding box is null");
        }

        if (this.color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        for (String uuids : section.getConfigurationSection("players").getKeys(false)) {
            final UUID uuid = UUID.fromString(uuids);
            final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            this.members.put(uuid, new GuildMember(section.getConfigurationSection("players." + uuid), this, player));
        }
    }

    public Guild(String name, Color color) {
        this.team = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(name);
        this.section = GuildManager.getConfig().createSection(name);
        this.boundingBox = new BoundingBox(0, 0, 0, 0, 0, 0);
        this.color = color;
        this.name = name;

        ConfigurationUtil.setBoundingBox(this.section, "bounding-box", boundingBox);
        ConfigurationUtil.setColor(this.section, "color", color);
        this.section.createSection("players");

        GuildManager.saveConfig();
    }
    
    public GuildMember addPlayer(OfflinePlayer player) {
        this.team.addEntry(player.getName());
        ConfigurationSection players = this.section.getConfigurationSection("players");
        GuildMember member = this.members.putIfAbsent(player.getUniqueId(),
                new GuildMember(players.createSection(player.getUniqueId().toString()), this, player));
        GuildManager.saveConfig();
        return member;
    }

    public GuildMember removePlayer(OfflinePlayer player) {
        this.team.removeEntry(player.getName());
        ConfigurationSection players = this.section.getConfigurationSection("players");
        players.set(player.getUniqueId().toString(), null);
        GuildManager.saveConfig();
        return this.members.remove(player.getUniqueId());
    }

    public String getName() { return this.name; }

    public GuildMember getMember(OfflinePlayer player) {
        return this.members.get(player.getUniqueId());
    }

    public Collection<GuildMember> getMembers() {
        return this.members.values();
    }

    public boolean hasMember(OfflinePlayer player) {
        return this.members.containsKey(player.getUniqueId());
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
