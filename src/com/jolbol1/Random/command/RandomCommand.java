package com.jolbol1.Random.command;


import com.jolbol1.Random.CommandHandler;
import com.jolbol1.Random.Coordinates;
import com.jolbol1.Random.RandomCoords;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RandomCommand extends CommandHandler {

    public static int move;

    public RandomCommand(RandomCoords plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        final Player player = (Player) sender;
        player.getUniqueId();
        final Location original = player.getLocation();

        int seconds = RandomCoords.getPlugin().getConfig()
                .getInt("TimeBeforeTeleport");
        int cool = RandomCoords.getPlugin().getConfig().getInt("Cooldown")
                + seconds;
        if (args.length < 1) {
            if (player.hasPermission("random.bypass")) {
                player.sendMessage(ChatColor.GOLD
                        + "[RandomCoords] Teleporting to a random coordinate");
                Bukkit.getServer().getPluginManager()
                        .callEvent(new Coordinates(player));
                player.getLocation().getChunk().load();

            } else {
                if (player.hasPermission("random.teleport")) {
                    Long time = RandomCoords.cooldown.get(player.getUniqueId());

                    // long secondsL =
                    // ((RandomCoords.cooldown.get(player.getUniqueId())/1000)+cool)
                    // - (System.currentTimeMillis()/1000);
                    if (time == null
                            || ((RandomCoords.cooldown
                            .get(player.getUniqueId()) / 1000) + cool)
                            - (System.currentTimeMillis() / 1000) >= cool
                            || ((RandomCoords.cooldown
                            .get(player.getUniqueId()) / 1000) + cool)
                            - (System.currentTimeMillis() / 1000) <= 0.1) {
                        player.sendMessage(ChatColor.GOLD + "Teleporting in "
                                + ChatColor.RED + seconds + ChatColor.GOLD
                                + " Seconds...");
                        RandomCoords.cooldown.put(player.getUniqueId(),
                                System.currentTimeMillis());


                        // BukkitScheduler scheduler =
                        // Bukkit.getServer().getScheduler();
                        // scheduler.scheduleSyncDelayedTask(this.plugin, new
                        // Runnable() {
                        move = Bukkit
                                .getServer()
                                .getScheduler()
                                .scheduleSyncDelayedTask(
                                        RandomCoords.getPlugin(),
                                        new Runnable() {
                                            public void run() {
                                                if (player.getLocation()
                                                        .distance(original) > 0.5) {
                                                    Bukkit.getScheduler()
                                                            .cancelTask(move);
                                                    player.sendMessage(ChatColor.GOLD
                                                            + "[RandomCoords] Teleportation Cancelled, Reason: You Moved");
                                                } else {
                                                    Bukkit.getServer().getPluginManager() .callEvent( new Coordinates(player));

                                                    player.getLocation()
                                                            .getChunk().load();
                                                    player.sendMessage(ChatColor.GREEN
                                                            + "Whoooshh.....");

                                                }
                                            }
                                        }

                                        , seconds * 20L);

                        // if(player.getLocation().distance(original) > 0.5) {
                        // Bukkit.getServer().getScheduler().cancelTask(move);
                        // player.sendMessage(ChatColor.GOLD +
                        // "[RandomCoords] Teleportation Cancelled - Reason: You Moved");
                        // }

                    } else {
                        long secondsLeft = ((RandomCoords.cooldown.get(player
                                .getUniqueId()) / 1000) + cool)
                                - (System.currentTimeMillis() / 1000);
                        player.sendMessage(ChatColor.GOLD
                                + "[RandomCoords] You have to wait "
                                + ChatColor.RED + secondsLeft + ChatColor.GOLD
                                + " second(s) before you can use this command");
                    }
                } else {
                    player.sendMessage(ChatColor.RED
                            + "You do not have the permission to use his command");
                }
            }

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.GOLD
                        + "[RandomCoords] RandomCoords, Coded by jolbol1");
                player.sendMessage(ChatColor.GOLD
                        + "/rc - Teleports you to a random location");
                player.sendMessage(ChatColor.GOLD
                        + "/rc <Player> - teleport someone else to a random location");
                player.sendMessage(ChatColor.GOLD
                        + "/rc reload - Reloads the config file");
                player.sendMessage(ChatColor.GOLD + "/rc help - Shows this");
            } else if (args[0].equalsIgnoreCase("reload")) {
                RandomCoords.getPlugin().reloadConfig();
                RandomCoords.config = YamlConfiguration
                        .loadConfiguration(RandomCoords.cfile);
                player.sendMessage(ChatColor.GOLD
                        + "[RandomCoords] Configuration Reloaded.");

            } else if (!(args[0].equalsIgnoreCase("reload"))) {
                if (player.hasPermission("random.others")) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(ChatColor.GOLD
                                + "[RandomCoords] Could not find player "
                                + args[0]);
                    } else {
                        player.sendMessage(ChatColor.GOLD
                                + "[RandomCoords] Teleporting "
                                + ChatColor.RED + target.getDisplayName()
                                + ChatColor.GOLD + " to a random location");
                        Bukkit.getServer().getPluginManager()
                                .callEvent(new Coordinates(target));
                        target.getLocation().getChunk().load();
                        target.sendMessage(ChatColor.GOLD
                                + "[RandomCoords] " + ChatColor.RED
                                + player.getName() + ChatColor.GOLD
                                + " teleported you to a random loaction");
                    }
                } else {
                    player.sendMessage(ChatColor.RED
                            + "You dont have the permission to perform this command");
                }

            }


        }
        return true;

    }

}