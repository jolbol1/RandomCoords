package com.jolbol1.Random.listeners;


import com.jolbol1.Random.Coordinates;
import com.jolbol1.Random.RandomCoords;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class Signs implements Listener {


    public Signs() {
        super();
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {

                Sign s = (Sign) b.getState();
                final Player p = e.getPlayer();
                String sl = ChatColor.BLUE + "[RandomCoord]";
                if (s.getLine(0).equalsIgnoreCase(sl)) {
                    if (p.hasPermission("random.sign")) {

                        if (RandomCoords.getPlugin().getConfig().getString("SignCooldown").equals("false")) {
                            Bukkit.getServer().getPluginManager().callEvent(new Coordinates(p));
                        } else if (RandomCoords.getPlugin().getConfig().getString("SignCooldown").equals("false")) {

                            Long time = RandomCoords.cooldown.get(p.getUniqueId());
                            int seconds = RandomCoords.getPlugin().getConfig().getInt("TimeBeforeTeleport");
                            int cool = RandomCoords.getPlugin().getConfig().getInt("Cooldown") + seconds;

                            //long secondsL = ((RandomCoords.cooldown.get(player.getUniqueId())/1000)+cool) - (System.currentTimeMillis()/1000);
                            if (time == null || ((RandomCoords.cooldown.get(p.getUniqueId()) / 1000) + cool) - (System.currentTimeMillis() / 1000) >= cool || ((RandomCoords.cooldown.get(p.getUniqueId()) / 1000) + cool) - (System.currentTimeMillis() / 1000) <= 0) {
                                RandomCoords.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                                p.sendMessage(ChatColor.GOLD + "Teleporting in " + ChatColor.RED + seconds + ChatColor.GOLD + " Seconds...");


                                BukkitScheduler schedule = Bukkit.getServer().getScheduler();
                                schedule.scheduleSyncDelayedTask(RandomCoords.getPlugin(), new Runnable() {
                                    public void run() {

                                        Bukkit.getServer().getPluginManager().callEvent(new Coordinates(p));

                                        p.sendMessage(ChatColor.GREEN + "Whoooshh.....");
                                    }
                                }
                                        , seconds * 20L);


                            } else {
                                long secondsLeft = ((RandomCoords.cooldown.get(p.getUniqueId()) / 1000) + cool) - (System.currentTimeMillis() / 1000);
                                p.sendMessage(ChatColor.GOLD + "[RandomCoords] You have to wait " + ChatColor.RED + secondsLeft + ChatColor.GOLD + " second(s) before you can use this sign");
                            }
                        }


                    } else {
                        p.sendMessage(ChatColor.RED + "You dont have permission to use this sign");
                    }

                }
            }
        }

    }


}


