package com.yolwoocle.midlplugin.guild.member;

import com.yolwoocle.midlplugin.guild.Guild;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class GuildMember {
    private final ConfigurationSection section;
    private final Guild guild;
    private final OfflinePlayer player;
    private GuildMemberBoard board;

    private final GuildMemberProperty<Integer> kills;
    private final GuildMemberProperty<Integer> deaths;
    private final GuildMemberProperty<Double> money;

    public GuildMember(ConfigurationSection section, Guild guild, OfflinePlayer player) {
        this.section = section;
        this.guild = guild;
        this.player = player;

        this.kills = new GuildMemberProperty<>(section, "kills", 0);
        this.deaths = new GuildMemberProperty<>(section, "deaths", 0);
        this.money = new GuildMemberProperty<>(section, "money", 0.0);
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public Guild getGuild() {
        return guild;
    }

    public int getKills() {
        return kills.get();
    }

    public GuildMemberBoard getBoard() {
        return board;
    }

    public void setBoard(GuildMemberBoard board) {
        this.board = board;
    }

    public void deleteBoard() {
        if (this.board == null)
            return;

        this.board.delete();
        this.board = null;
    }

    public void setKills(int kills) {
        this.kills.set(kills);
    }

    public void addKills(int kills) {
        this.kills.set(this.kills.get() + kills);
    }

    public int getDeaths() {
        return deaths.get();
    }

    public void setDeaths(int deaths) {
        this.deaths.set(deaths);
    }

    public void addDeaths(int deaths) {
        this.deaths.set(this.deaths.get() + deaths);
    }

    public double getMoney() {
        return money.get();
    }

    public void setMoney(double money) {
        this.money.set(money);
    }

    public void addMoney(double money) {
        this.money.set(this.money.get() + money);
    }

    public void removeMoney(double money) {
        this.money.set(this.money.get() - money);
    }
}
