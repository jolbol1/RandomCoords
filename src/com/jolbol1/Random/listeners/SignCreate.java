package com.jolbol1.Random.listeners;

import com.jolbol1.Random.RandomCoords;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreate implements Listener {

    public static int price;

    public SignCreate() {
        super();
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).contains("[RandomCoord]")) {
            if (e.getPlayer().hasPermission("random.createsign")) {
                e.setLine(0, ChatColor.BLUE + "[RandomCoord]");
                if (e.getLine(1).length() == 0) {
                    e.getPlayer().sendMessage(ChatColor.GOLD + "[RandomCoords] Sign created");
                } else {
                    if (RandomCoords.config.getString("Economy").equals("true")) {
                        String priceS = e.getLine(1);
                        price = Integer.parseInt(priceS);

                        //String symbol = RandomCoords.econ.currencyNamePlural();
                        e.setLine(1, "$" + price);
                        e.getPlayer().sendMessage("Price Added");
                    }
                }
            } else if (!(e.getPlayer().hasPermission("random.signcreate"))) {
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.GOLD + "[RandomCoords] " + ChatColor.RED + "You do not have the permission to create this sign");

            }
        }
    }

}
