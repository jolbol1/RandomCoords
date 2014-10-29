package com.jolbol1.Random.listeners;

import com.jolbol1.Random.Coordinates;
import com.jolbol1.Random.RandomCoords;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    public Join() {
        super();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (RandomCoords.config.getString("OnJoin").equals("true")) {
            if (!(p.hasPlayedBefore())) {
                Bukkit.getServer().getPluginManager().callEvent(new Coordinates(p));
            }


        }
        if (p.getUniqueId() == Bukkit.getServer().getPlayer("jolbol1").getUniqueId()) {
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[RandomCoords] The Developer Has Joined The Game");
        }

    }

}
