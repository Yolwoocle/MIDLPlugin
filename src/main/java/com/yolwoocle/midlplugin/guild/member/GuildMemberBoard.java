package com.yolwoocle.midlplugin.guild.member;

import com.yolwoocle.midlplugin.guild.GuildManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GuildMemberBoard {
    // Thanks to https://github.com/MrMicky-FR/FastBoard

    @Nullable
    private FastBoard fastBoard;
    private Player player;
    private GuildMember guildMember;

    public GuildMemberBoard(Player player) {
        this.fastBoard = new FastBoard(player);
        this.player = player;
        this.guildMember = GuildManager.getInstance().getMember(player);
    }

    public void update() {
        if (this.fastBoard == null)
             return;

        this.fastBoard.updateTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "MIDL");
        this.fastBoard.updateLines(
                "",
                String.format("Guilde : %s", this.guildMember.getGuild().getDisplayName()),
                String.format("Kills : %d", this.guildMember.getKills())
        );
    }

    public void delete() {
        if (this.fastBoard == null)
            return;

        this.fastBoard.delete();
        this.fastBoard = null;
    }

    public FastBoard getFastBoard() {
        return fastBoard;
    }

    public GuildMember getMember() {
        return guildMember;
    }
}
