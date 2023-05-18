package de.gamingfelin.simpletimer;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SimpleTimer extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Initialising Simple Timer Plugin!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
