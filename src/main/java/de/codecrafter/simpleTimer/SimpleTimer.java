package de.codecrafter.simpleTimer;

import de.codecrafter.simpleTimer.commands.TimerCommand;
import de.codecrafter.simpleTimer.models.Timer;
import de.codecrafter.simpleTimer.utils.TimerConfig;
import de.codecrafter.simpleTimer.utils.TimerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import static de.codecrafter.simpleTimer.utils.Formatter.formatTime;

public final class SimpleTimer extends JavaPlugin {
    private static SimpleTimer plugin;
    private TimerConfig timerConfig;
    private TimerManager timerManager;
    private Timer activeTimer;

    private boolean showPauseString;

    @Override
    public void onEnable() {
        plugin = this;

        // load config
        saveDefaultConfig();
        timerConfig = new TimerConfig(this);

        // load TimerManager and activeTimer
        timerManager = new TimerManager(this);
        activeTimer = timerManager.getActiveTimer();

        // create default timer if no timer exists
        if (activeTimer == null) {
            activeTimer = new Timer("default", 0L, false);
            timerManager.addTimer(activeTimer);
            timerManager.setActiveTimer(activeTimer.getName());
        }

        // register commands
        getCommand("timer").setExecutor(new TimerCommand());

        // init timer vars
        showPauseString = true;

        // the timer scheduler
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskTimer(this, () -> {
            // only when players are online
            if (!getServer().getOnlinePlayers().isEmpty()) {
                if (activeTimer.isRunning()) {
                    activeTimer.addTime();

                    if (timerConfig.isShow()) {
                        getServer().getOnlinePlayers().forEach(player -> {
                            player.sendActionBar(Component.text(formatTime(activeTimer.getTime()))
                                    .color(getTimerConfig().getColorRunning()));
                        });
                    }
                } else {
                    if (timerConfig.isShow()) {
                        getServer().getOnlinePlayers().forEach(player -> {
                            if (showPauseString && timerConfig.isShowPauseMessage()) {
                                player.sendActionBar(Component.text(timerConfig.getPauseMessage())
                                        .color(getTimerConfig().getColorPaused()));
                            } else {
                                player.sendActionBar(Component.text(formatTime(activeTimer.getTime()))
                                        .color(getTimerConfig().getColorPauseMessage()));
                            }
                        });
                    }
                    showPauseString = !showPauseString;
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        timerManager.saveToFile();
    }

    public static SimpleTimer getPlugin() {
        return plugin;
    }

    public TimerConfig getTimerConfig() {
        return timerConfig;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public Timer getActiveTimer() {
        return activeTimer;
    }
}
