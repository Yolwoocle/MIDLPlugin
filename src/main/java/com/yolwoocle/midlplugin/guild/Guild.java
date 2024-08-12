package com.yolwoocle.midlplugin.guild;

import com.yolwoocle.midlplugin.guild.member.GuildMember;
import com.yolwoocle.midlplugin.utils.ChatUtil;
import com.yolwoocle.midlplugin.utils.ConfigurationUtil;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BoundingBox;

import java.util.*;
import java.util.function.Consumer;

public class Guild {
    private final ConfigurationSection section;
    private final HashMap<UUID, GuildMember> members = new HashMap<>();
    private Color color;
    private final Team team;
    private final BoundingBox boundingBox;
    private final String name;
    private final HashSet<NamespacedKey> advancements = new HashSet<>();

    public Guild(ConfigurationSection section) {
        this.section = section;
        this.name = section.getName();
        this.boundingBox = ConfigurationUtil.getBoundingBox(section.getConfigurationSection("bounding-box"));
        this.color = ConfigurationUtil.getColor(section, "color");
        this.team = createTeam();

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

    public Guild(String name) {
        this.section = GuildManager.getConfig().createSection(name);
        this.boundingBox = new BoundingBox(0, 0, 0, 0, 0, 0);
        this.color = Color.WHITE;
        this.name = name;
        this.team = createTeam();

        ConfigurationUtil.setBoundingBox(this.section, "bounding-box", boundingBox);
        ConfigurationUtil.setColor(this.section, "color", color);
        this.section.createSection("players");

        GuildManager.saveConfig();
    }

    private Team createTeam() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;

        Team team = scoreboardManager.getNewScoreboard().registerNewTeam(this.name);
        team.setPrefix(ChatColor.WHITE + "[" + this.getDisplayName() + ChatColor.WHITE + "] " + ChatUtil.colorToString(this.color));
        team.setCanSeeFriendlyInvisibles(true);
        team.setAllowFriendlyFire(false);
        return team;
    }

    public GuildMember addPlayer(OfflinePlayer player) {
        this.team.addEntry(player.getName());
        ConfigurationSection players = this.section.getConfigurationSection("players");

        GuildMember member = new GuildMember(players.createSection(player.getUniqueId().toString()), this, player);
        this.members.putIfAbsent(player.getUniqueId(), member);

        GuildManager.saveConfig();

        if (member != null) {
            member.awardAdvancements(this.advancements);
        }
        return member;
    }

    public GuildMember removePlayer(OfflinePlayer player) {
        this.team.removeEntry(player.getName());
        ConfigurationSection players = this.section.getConfigurationSection("players");
        players.set(player.getUniqueId().toString(), null);
        GuildManager.saveConfig();
        return this.members.remove(player.getUniqueId());
    }

    public void forEachMember(Consumer<GuildMember> consumer) {
        for (GuildMember member: this.members.values()) {
            consumer.accept(member);
        }
    }

    public void onMemberJoinServer(GuildMember member) {
        member.awardAdvancements(this.advancements);
    }

    public void onNewAdvancementDone(Advancement advancement) {
        this.advancements.add(advancement.getKey());

        this.forEachMember((member) -> {
            member.awardAdvancement(advancement);
        });
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public HashSet<NamespacedKey> getAdvancements() {
        return advancements;
    }

    public Team getTeam() {
        return team;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() { return this.name; }

    public String getDisplayName() {
        return ChatUtil.colorToString(color) + this.name + ChatColor.RESET;
    }

    public GuildMember getMember(OfflinePlayer player) {
        return this.members.get(player.getUniqueId());
    }

    public Collection<GuildMember> getMembers() {
        return this.members.values();
    }

    public boolean hasMember(OfflinePlayer player) {
        return this.members.containsKey(player.getUniqueId());
    }
    
    public int getKills() {
        return this.members.values().stream().mapToInt(GuildMember::getKills).sum();
    }

    public int getDeaths() {
        return this.members.values().stream().mapToInt(GuildMember::getDeaths).sum();
    }

    public double getMoney() {
        return this.members.values().stream().mapToDouble(GuildMember::getMoney).sum();
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
