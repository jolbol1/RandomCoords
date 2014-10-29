package com.jolbol1.Random;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Random;

import static org.bukkit.block.BlockFace.UP;

public class Coordinates extends Event {


    private static final HandlerList handlers = new HandlerList();
    Player p;


    public Coordinates(Player p) {
        this.p = p;

        //Generate Random Coordinates
        Random r = new Random();
        double max = RandomCoords.config.getDouble("MaxCoordinate");
        double min = RandomCoords.config.getDouble("MinCoordinate");
        double x1 = r.nextDouble();
        double x2 = min + (max - min) * x1;
        double z1 = r.nextDouble();
        double z2 = min + (max - min) * z1;
        double y = p.getWorld().getHighestBlockYAt((int) x2, (int) z2);
        final Location l1;
        l1 = new Location(p.getWorld(), x2, y, z2);
        Chunk c = l1.getChunk();
        //Check if block is water.
        if (!l1.getBlock().getRelative(BlockFace.DOWN).isLiquid() && l1.getBlock().getRelative(UP).isEmpty()) {
            l1.getChunk().load();
            l1.getChunk().load(true);
            if (RandomCoords.config.getString("Factions").equals("false")) {
                p.teleport(l1);
                while (!c.isLoaded()) {
                    c.load();
                }
                p.teleport(l1);
            } else if (RandomCoords.config.getString("Factions").equals("true")) {
                Faction f = BoardColls.get().getFactionAt(PS.valueOf(l1));


                if (f.isNone()) {

                    p.teleport(l1);
                    while (!c.isLoaded()) {
                        c.load();
                    }
                    p.teleport(l1);

                } else {
                    Bukkit.getServer().getPluginManager().callEvent(new Coordinates(p));
                }
            }

        } else if (l1.getBlock().getRelative(BlockFace.DOWN).isLiquid() || !l1.getBlock().getRelative(UP).isEmpty()) {
            Bukkit.getServer().getPluginManager().callEvent(new Coordinates(p));
        }

    }

    public HandlerList getHandlers() {
        return handlers;
    }


}
