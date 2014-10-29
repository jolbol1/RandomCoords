package com.jolbol1.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandHandler implements CommandExecutor {
    protected final RandomCoords plugin;

    public CommandHandler(RandomCoords plugin) {
        this.plugin = plugin;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}