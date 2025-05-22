package de.codecrafter.simpleTimer.utils;

import de.codecrafter.simpleTimer.SimpleTimer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Provides the config for the plugin.
 */
public class TimerConfig {
    private final SimpleTimer plugin;

    // config values
    private boolean show;
    private boolean showPauseMessage;
    private String pauseMessage;
    private NamedTextColor colorRunning;
    private NamedTextColor colorPaused;
    private NamedTextColor colorPauseMessage;

    /**
     * Creates an instance of {@code TimerConfig} class.
     *
     * @param plugin The plugin.
     */
    public TimerConfig(SimpleTimer plugin) {
        this.plugin = plugin;
        this.load(plugin.getConfig());
    }

    /**
     * Reloads the config from the config file on the disk.
     */
    public void reload() {
        this.plugin.reloadConfig();
        this.load(plugin.getConfig());
        this.plugin.getComponentLogger().info("Timer config reloaded.");
    }

    /**
     * Populates the attributes of the class instance with the values from the config file.
     *
     * @param config The config object.
     */
    private void load(FileConfiguration config) {
        this.show = config.getBoolean("timer.show", true);
        this.showPauseMessage = config.getBoolean("timer.show_pause_message", true);
        this.pauseMessage = config.getString("timer.pause_message", "Timer paused");
        this.colorRunning = NamedTextColor.NAMES.value(config.getString("timer.color_running", "YELLOW").toLowerCase());
        this.colorPaused = NamedTextColor.NAMES.value(config.getString("timer.color_paused", "GOLD").toLowerCase());
        this.colorPauseMessage = NamedTextColor.NAMES.value(config.getString("timer.color_pause_message", "GOLD").toLowerCase());
    }

    public boolean isShow() {
        return show;
    }

    public boolean isShowPauseMessage() {
        return showPauseMessage;
    }

    public String getPauseMessage() {
        return pauseMessage;
    }

    public NamedTextColor getColorRunning() {
        return colorRunning;
    }

    public NamedTextColor getColorPaused() {
        return colorPaused;
    }

    public NamedTextColor getColorPauseMessage() {
        return colorPauseMessage;
    }
}
