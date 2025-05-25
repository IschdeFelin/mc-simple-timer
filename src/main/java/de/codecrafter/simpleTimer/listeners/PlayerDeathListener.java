package de.codecrafter.simpleTimer.listeners;

import de.codecrafter.simpleTimer.SimpleTimer;
import de.codecrafter.simpleTimer.models.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static de.codecrafter.simpleTimer.utils.Formatter.formatTime;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        SimpleTimer plugin = SimpleTimer.getPlugin();
        Timer timer = plugin.getActiveTimer();
        if (timer == null) return;
        timer.setRunning(false);

        if (plugin.getTimerConfig().isPauseOnPlayerDeath()) {
            plugin.getServer().getOnlinePlayers().forEach(player ->
                    player.sendMessage("Timer stopped at " + formatTime(timer.getTime()) + "."));
        }
    }
}
