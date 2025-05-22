package de.codecrafter.simpleTimer.commands;

import de.codecrafter.simpleTimer.SimpleTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be used by players.");
            return true;
        }

        SimpleTimer simpleTimer = SimpleTimer.getPlugin();
        if (strings.length == 1) {
            switch (strings[0].toLowerCase()) {
                case "pause" -> {
                    simpleTimer.setRunning(false);
                    return true;
                }
                case "resume" -> {
                    simpleTimer.setRunning(true);
                    return true;
                }
                case "reset" -> {
                    simpleTimer.setRunning(false);
                    simpleTimer.setTime(0L);
                    return true;
                }
                case "reload" -> {
                    simpleTimer.getTimerConfig().reload();
                    commandSender.sendMessage("Configuration reloaded.");
                    return true;
                }
            }
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("set")) {
                try {
                    simpleTimer.setRunning(false);
                    simpleTimer.setTime(Long.parseLong(strings[1]));
                } catch (Exception e) {
                    commandSender.sendMessage("Please enter a valid number for the time.");
                }
                return true;
            }
        }

        commandSender.sendMessage("Usage: /timer <pause|resume|reset|reload|set> [time_in_seconds]");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> options = List.of("pause", "resume", "reset", "reload", "set");
        List<String> completions = new ArrayList<>();

        if (strings.length == 1) {
            for (String string : options){
                if (string.toLowerCase().startsWith(strings[0].toLowerCase())) {
                    completions.add(string);
                }
            }
        } else {
            completions.add("");
        }

        return completions;
    }
}
