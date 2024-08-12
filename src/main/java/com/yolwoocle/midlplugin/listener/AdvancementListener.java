package com.yolwoocle.midlplugin.listener;

import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Guild guild = GuildManager.getInstance().getGuild(player);
        if (guild == null)
            return;

        guild.onNewAdvancementDone(event.getAdvancement());
    }
}
