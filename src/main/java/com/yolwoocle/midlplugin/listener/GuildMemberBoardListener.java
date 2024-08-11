package com.yolwoocle.midlplugin.listener;

import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.guild.member.GuildMember;
import com.yolwoocle.midlplugin.guild.member.GuildMemberBoard;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.BoundingBox;

import java.awt.*;

public class GuildMemberBoardListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        GuildMember guildMember = GuildManager.getInstance().getMember(player);
        if (guildMember == null)
            return;
        if (guildMember.getBoard() != null) {
            guildMember.deleteBoard();
        }

        GuildMemberBoard board = new GuildMemberBoard(player);
        guildMember.setBoard(board);
        board.update();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        GuildMember guildMember = GuildManager.getInstance().getMember(player);
        if (guildMember == null)
            return;

        guildMember.deleteBoard();
    }
}
