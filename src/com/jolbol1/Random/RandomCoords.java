package com.jolbol1.Random;

import com.jolbol1.Random.command.RandomCommand;
import com.jolbol1.Random.listeners.Join;
import com.jolbol1.Random.listeners.SignCreate;
import com.jolbol1.Random.listeners.Signs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


public class RandomCoords extends JavaPlugin {
    //Setup Economy

    //Add Colour to consoles
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BOLD = "\u001B[1m";
    //Setup configuration
    public static FileConfiguration config;
    public static File cfile;
    //Set up cooldown using UUID's, supports name changes
    public static Map<UUID, Long> cooldown = new HashMap<UUID, Long>();
    //Call Plugin
    private static Plugin plugin;
    //Setup the Logger
    public final Logger log = Logger.getLogger("com.jolbol1.RandomCoords");


    //private final PlayerListener playerListener = new PlayerListener();

    //Call getPlugin()
    public static Plugin getPlugin() {
        return plugin;
    }

    public void onEnable() {

        // Define plugin
        plugin = this;

        //Setup Metrics
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) { // Failed to submit the stats :-(
            System.out.println("Error Submitting stats!");
        }

        //Call plugin manager, Get Decription
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdf = getDescription();

        //Recall Configuration file
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        cfile = new File(getDataFolder(), "config.yml");

        //Register our events
        // Template
        //pm.registerEvents(new Template(this), this);
        pm.registerEvents(new Signs(), this);
        pm.registerEvents(new Join(), this);
        pm.registerEvents(new SignCreate(), this);


        //Register commands
        getCommand("rc").setExecutor(new RandomCommand(this));


        //What to write when enabled
        log.info(ANSI_BLUE + ANSI_BOLD + "[" + pdf.getName() + "] version " + pdf.getVersion() + " enabled." + ANSI_RESET);
    }

    public void onDisable() {
        //Stop memory leaks
        plugin = null;
        cooldown = null;


        PluginDescriptionFile pdf = getDescription();
        log.info(ANSI_BLUE + ANSI_BOLD + "[" + pdf.getName() + "] version " + pdf.getVersion() + " Disabled.");
    }


}