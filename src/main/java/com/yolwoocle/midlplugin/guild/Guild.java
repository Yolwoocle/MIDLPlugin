package com.yolwoocle.midlplugin.guild;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Guild {
    private final ConfigurationSection section;
    private HashMap<UUID, GuildMember> members;

    public Guild(ConfigurationSection section) {
        this.section = section;
    }
    
    public void addPlayer(OfflinePlayer player) {
        this.members.put(player.getUniqueId(), new GuildMember());
    }

    public Set<UUID> getPlayers() {
        return this.members.keySet();
    }
}
