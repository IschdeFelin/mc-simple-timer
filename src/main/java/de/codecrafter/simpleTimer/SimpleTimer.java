/*
 * This file is part of the Minecraft Simple Timer project.
 * Licensed under the MIT License. See the LICENSE file in the project root for full license information.
 */

package de.codecrafter.simpleTimer;

import de.codecrafter.simpleTimer.commands.TimerCommand;
import de.codecrafter.simpleTimer.listeners.PlayerDeathListener;
import de.codecrafter.simpleTimer.listeners.PlayerJoinListener;
import de.codecrafter.simpleTimer.models.Timer;
import de.codecrafter.simpleTimer.utils.TimerConfig;
import de.codecrafter.simpleTimer.utils.TimerManager;
import de.codecrafter.simpleTimer.utils.UpdateChecker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;

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

        // get scheduler
        BukkitScheduler scheduler = getServer().getScheduler();

        // check for updates and create scheduler
        UpdateChecker updateChecker = new UpdateChecker(this);
        scheduler.runTaskTimerAsynchronously(this, updateChecker::check, 0, 72000);

        // load TimerManager and activeTimer
        timerManager = new TimerManager(this);
        activeTimer = timerManager.getActiveTimer();

        // check if activeTimer is set
        if (activeTimer == null) {
            // if not and no timers exist create the default timer
            if (timerManager.getTimerNames().isEmpty()) {
                activeTimer = new Timer("default", 0L, false);
                timerManager.addTimer(activeTimer);
                timerManager.setActiveTimer(activeTimer.getName());
            } else {
                // else select the first timer from timerManager
                activeTimer =  timerManager.getTimer(timerManager.getTimerNames().getFirst());
            }
        }

        // register commands
        getCommand("timer").setExecutor(new TimerCommand());

        // register event listeners
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(updateChecker), this);

        // init timer vars
        showPauseString = true;

        // the timer scheduler
        scheduler.runTaskTimer(this, () -> {
            Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();

            // only when players are online or runWithoutPlayers == true
            if (!onlinePlayers.isEmpty() || getTimerConfig().isRunWithoutPlayers()) {
                if (activeTimer == null) {
                    onlinePlayers.forEach(player ->
                            player.sendActionBar(Component.text("No timer selected")
                            .color(NamedTextColor.RED)));
                    return;
                }

                if (activeTimer.isRunning()) {
                    if (!activeTimer.addTime()) {
                        activeTimer.setRunning(false);
                        onlinePlayers.forEach(player ->
                                player.sendMessage(Component.text("Timer reached maximum duration!")
                                        .color(NamedTextColor.RED)));
                        return;
                    }

                    onlinePlayers.forEach(player ->
                            player.sendActionBar(Component.text(formatTime(activeTimer.getTime()))
                                    .color(getTimerConfig().getColorRunning())));
                } else {
                    onlinePlayers.forEach(player -> {
                        if (showPauseString && timerConfig.isShowPauseMessage()) {
                            player.sendActionBar(Component.text(timerConfig.getPauseMessage())
                                    .color(getTimerConfig().getColorPauseMessage()));
                        } else {
                            player.sendActionBar(Component.text(formatTime(activeTimer.getTime()))
                                    .color(getTimerConfig().getColorPaused()));
                        }
                    });
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

    public void setActiveTimer(Timer activeTimer) {
        this.activeTimer = activeTimer;
        timerManager.setActiveTimer(activeTimer != null ? activeTimer.getName() : null);
    }
}
