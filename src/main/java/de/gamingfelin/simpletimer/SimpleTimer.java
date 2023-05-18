package de.gamingfelin.simpletimer;

import de.gamingfelin.simpletimer.commands.TimerCommand;
import de.gamingfelin.simpletimer.utilitys.TimerFormatter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;

public final class SimpleTimer extends JavaPlugin {
    private int time;
    private BukkitRunnable runnable;
    private static SimpleTimer plugin;
    public boolean isRunning;
    private boolean pauseString;
    private ChatColor timerColor;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        time = 0;
        isRunning = false;
        pauseString = true;
        getCommand("timer").setExecutor(new TimerCommand());
        timerColor = ChatColor.YELLOW;

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (isRunning) {
                    time++;
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerColor + TimerFormatter.formatTime(time)));
                    });
                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (pauseString) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerColor + "Timer paused"));
                        } else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerColor + TimerFormatter.formatTime(time)));
                        }
                    });
                    pauseString = !pauseString;
                }
            }
        };
        runnable.runTaskTimer(this, 0, 20);
        getLogger().info("Initialising Simple Timer Plugin!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SimpleTimer getPlugin() {
        return plugin;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
