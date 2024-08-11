package com.yolwoocle.midlplugin.listener;


import com.yolwoocle.midlplugin.guild.Guild;
import com.yolwoocle.midlplugin.guild.GuildManager;
import com.yolwoocle.midlplugin.guild.member.GuildMember;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class GuildMemberListener implements Listener {

    @EventHandler
    public void playerKillPlayer(PlayerDeathEvent e)
    {
        Player victim = e.getEntity();
        GuildManager guildManager = GuildManager.getInstance();
        GuildMember victimMember = guildManager.getMember(victim);

        if (victimMember == null)
            return;

        victimMember.addDeaths(1);

        Entity killerEntity = e.getEntity().getKiller();
        if (!(killerEntity instanceof Player killer))
            return;

        GuildMember killerMember = guildManager.getMember(killer);
        if (killerMember == null || killerMember.equals(victimMember))
            return;

        killerMember.addKills(1);
    }

    @EventHandler
    public void cancelForbiddenBreakBlock(BlockBreakEvent e){
        Player breaker = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null || guild.hasMember(breaker))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void cancelForbiddenPlaceBlock(BlockPlaceEvent e){
        Player placer = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null || guild.hasMember(placer))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void cancelForbiddenExplosionBlock(BlockExplodeEvent e){
        Block block = e.getBlock();
        Location location = block.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null)
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void cancelForbiddenInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null)
            return;

        Location location = block.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null || guild.hasMember(player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void cancelForbiddenDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Player player))
            return;

        Location location = player.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null || !guild.hasMember(player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void cancelForbiddenMobSpawnEvent(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Monster monster))
            return;

        Location location = monster.getLocation();
        Guild guild = GuildManager.getInstance().getGuild(location);

        if (guild == null)
            return;

        event.setCancelled(true);
    }

}
