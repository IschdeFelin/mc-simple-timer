package de.codecrafter.simpleTimer.utils;

import de.codecrafter.simpleTimer.SimpleTimer;
import de.codecrafter.simpleTimer.models.Timer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Provides the management of all created timers. The timers are saved in the {@code timers.yml} file.
 * Also store the current active timer.
 */
public class TimerManager {
    private final SimpleTimer plugin;
    private final File file;
    private final YamlConfiguration data;

    private final Map<String, Timer> timers;
    private Timer activeTimer;

    /**
     * Creates an instance of the {@code TimerManager} class.
     * For this all timers are read from the config file and saved in to the {@code timers} attribute.
     * The active timer is saved in the {@code activeTimer} attribute.
     *
     * @param plugin The plugin.
     */
    public TimerManager(SimpleTimer plugin) {
        this.plugin = plugin;

        // create 'timers.yml' file if it doesn't exist
        this.file = new File(plugin.getDataFolder(), "timers.yml");
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getComponentLogger().error("Error while creating timers.yml file.", e);
            }
        }

        // load the yaml config
        this.data = YamlConfiguration.loadConfiguration(file);

        // initialize attributes
        this.timers = loadTimersFromDisk();
        this.activeTimer = timers.get(data.getString("active", null));
    }

    /**
     * Returns a map of {@code timerName} and {@code timerObject} from the data file.
     *
     * @return The map of all timers.
     */
    private Map<String, Timer> loadTimersFromDisk() {
        Map<String, Timer> loadedTimers = new HashMap<>();

        var section = data.getConfigurationSection("timers");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                long time = data.getLong("timers." + key + ".time", 0L);
                boolean running = data.getBoolean("timers." + key + ".running", false);
                loadedTimers.put(key, new Timer(key, time, running));
            }
        }

        return loadedTimers;
    }

    /**
     * Sets the give timer as the {@code activeTimer}.
     *
     * @param name The name of the timer to set as active.
     */
    public void setActiveTimer(String name) {
        if (name == null || !timers.containsKey(name)) {
            activeTimer = null;
        } else {
            activeTimer = timers.get(name);
        }
    }

    /**
     * Returns the active timer or {@code null}.
     *
     * @return The active timer object.
     */
    public Timer getActiveTimer() {
        if (activeTimer != null && timers.containsValue(activeTimer)) {
            return activeTimer;
        }

        return null;
    }

    /**
     * Returns the timer with the given name or {@code null}.
     *
     * @param name The name of the wanted timer.
     * @return The timer object.
     */
    public Timer getTimer(String name) {
        return name == null ? null : timers.get(name);
    }

    /**
     * Returns a list of all timer names.
     *
     * @return A list with all timer names.
     */
    public List<String> getTimerNames() {
        return new ArrayList<>(timers.keySet());
    }

    /**
     * Adds a time to the {@code TimerManager}.
     *
     * @param timer The timer object to add.
     */
    public void addTimer(Timer timer) {
        timers.put(timer.getName(), timer);
    }

    /**
     * Removes the given timer from the {@code TimerManager}.
     *
     * @param timer The timer to be removed.
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer.getName());
        if (timer.equals(activeTimer)) {
            setActiveTimer(null);
        }
    }

    /**
     * Saves all the data from the {@code TimerManager} to the {@code timers.yml} file.
     */
    public void saveToFile() {
        // save the active timer
        if (activeTimer != null && timers.containsValue(activeTimer)) {
            data.set("active", activeTimer.getName());
        } else {
            data.set("active", null);
        }

        // clear timers
        data.set("timers", null);

        // save all timers
        timers.forEach((key, timer) -> {
            data.set("timers." + key + ".time", timer.getTime());
            data.set("timers." + key + ".running", timer.isRunning());
        });

        try {
            data.save(file);
        } catch (IOException e) {
            plugin.getComponentLogger().error("Error saving timers.yml", e);
        }
    }
}
