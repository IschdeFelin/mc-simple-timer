package de.codecrafter.simpleTimer;

import de.codecrafter.simpleTimer.commands.TimerCommand;
import de.codecrafter.simpleTimer.utils.TimerConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import static de.codecrafter.simpleTimer.utils.Formatter.formatTime;

public final class SimpleTimer extends JavaPlugin {
    private static SimpleTimer plugin;
    private TimerConfig timerConfig;
    private long time;
    private boolean isRunning;
    private boolean showPauseString;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        timerConfig = new TimerConfig(this);

        // register commands
        getCommand("timer").setExecutor(new TimerCommand());

        // init timer vars
        time = 0;
        isRunning = false;
        showPauseString = true;

        // the timer scheduler
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskTimer(this, () -> {
            if (isRunning) {
                time++;
                if (timerConfig.isShow()) {
                    getServer().getOnlinePlayers().forEach(player -> {
                        player.sendActionBar(Component.text(formatTime(time))
                                .color(getTimerConfig().getColorRunning()));
                    });
                }
            } else {
                getServer().getOnlinePlayers().forEach(player -> {
                    if (timerConfig.isShow()) {
                        if (showPauseString && timerConfig.isShowPauseMessage()) {
                            player.sendActionBar(Component.text(timerConfig.getPauseMessage())
                                    .color(getTimerConfig().getColorPaused()));
                        } else {
                            player.sendActionBar(Component.text(formatTime(time))
                                    .color(getTimerConfig().getColorPauseMessage()));
                        }
                    }
                });
                showPauseString = !showPauseString;
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SimpleTimer getPlugin() {
        return plugin;
    }

    public TimerConfig getTimerConfig() {
        return timerConfig;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
