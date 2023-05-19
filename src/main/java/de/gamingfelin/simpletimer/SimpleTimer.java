package de.gamingfelin.simpletimer;

import de.gamingfelin.simpletimer.commands.TimerCommand;
import de.gamingfelin.simpletimer.commands.TimercCommand;
import de.gamingfelin.simpletimer.utilitys.TimerFormatter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SimpleTimer extends JavaPlugin {
    private int time;
    private static SimpleTimer plugin;
    public boolean isRunning;
    private boolean pauseString;

    // Colors
    public ChatColor timerColor;
    public ChatColor timerPauseColor;
    @Override
    public void onEnable() {
        plugin = this;

        // Register commands
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("timerc").setExecutor(new TimercCommand());

        // Initial Config
        saveDefaultConfig();

        // Get colors
        FileConfiguration config = getConfig();
        String timerColorString = config.getString("colors.timer");
        String timerPauseColorString = config.getString("colors.timer-pause");
        try {
            timerColor = ChatColor.valueOf(timerColorString);
            timerPauseColor = ChatColor.valueOf(timerPauseColorString);
        } catch (Exception e) {
            getLogger().severe("Error reading colors from config!");
        }

        // Set vars
        time = 0;
        isRunning = false;
        pauseString = true;

        BukkitRunnable runnable = new BukkitRunnable() {
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
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerPauseColor + "Timer paused"));
                        } else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerPauseColor + TimerFormatter.formatTime(time)));
                        }
                    });
                    pauseString = !pauseString;
                }
            }
        };
        runnable.runTaskTimer(this, 0, 20);
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
