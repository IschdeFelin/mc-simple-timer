package de.codecrafter.simpleTimer.commands;

import de.codecrafter.simpleTimer.SimpleTimer;
import de.codecrafter.simpleTimer.models.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static de.codecrafter.simpleTimer.utils.Formatter.formatTime;

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
                    simpleTimer.getActiveTimer().setRunning(false);
                    return true;
                }
                case "resume" -> {
                    simpleTimer.getActiveTimer().setRunning(true);
                    return true;
                }
                case "reset" -> {
                    simpleTimer.getActiveTimer().setRunning(false);
                    simpleTimer.getActiveTimer().setTime(0L);
                    return true;
                }
                case "reload" -> {
                    simpleTimer.getTimerConfig().reload();
                    commandSender.sendMessage("Configuration reloaded.");
                    return true;
                }
                case "save" -> {
                    simpleTimer.getTimerManager().saveToFile();
                    commandSender.sendMessage("Timers saved.");
                    return true;
                }
                case "list" -> {
                    List<Timer> allTimers = simpleTimer.getTimerManager().getAllTimers();

                    if (allTimers.isEmpty()) {
                        commandSender.sendMessage("There are no timers.");
                        return true;
                    }

                    allTimers.sort(Comparator.comparing(Timer::getName));
                    Component timersFormatted = Component.join(
                            JoinConfiguration.newlines(),
                            allTimers.stream().map(timer ->
                                    Component.text("- " + timer.getName() + " (" + formatTime(timer.getTime()) + ")")
                                            .hoverEvent(HoverEvent.showText(Component.text("Click to select")))
                                            .clickEvent(ClickEvent.suggestCommand("/timer select " + timer.getName()))
                            ).toList()
                    );

                    commandSender.sendMessage(Component.text("These timers exist:")
                            .append(Component.newline())
                            .append(timersFormatted));
                    return true;
                }
                case "name" -> {
                    if (simpleTimer.getActiveTimer() == null) {
                        commandSender.sendMessage("No timer selected.");
                        return true;
                    }

                    String activeTimerName = simpleTimer.getActiveTimer().getName();
                    commandSender.sendMessage("The current timer is: " + activeTimerName);
                    return true;
                }
            }
        } else if (strings.length == 2) {
            switch (strings[0].toLowerCase()) {
                case "set" -> {
                    try {
                        simpleTimer.getActiveTimer().setRunning(false);
                        simpleTimer.getActiveTimer().setTime(Long.parseLong(strings[1]));
                    } catch (Exception e) {
                        commandSender.sendMessage("Please enter a valid number for the time.");
                    }
                    return true;
                }
                case "select" -> {
                    Timer timer = simpleTimer.getTimerManager().getTimer(strings[1]);

                    if (timer == null) {
                        commandSender.sendMessage("This timer does not exist.");
                        return true;
                    }

                    simpleTimer.setActiveTimer(timer);
                    simpleTimer.getActiveTimer().setRunning(false);
                    return true;
                }
                case "remove" -> {
                    Timer timer = simpleTimer.getTimerManager().getTimer(strings[1]);

                    if (timer == null) {
                        commandSender.sendMessage("This timer does not exist.");
                        return true;
                    }

                    simpleTimer.getTimerManager().removeTimer(timer);
                    if (Objects.equals(simpleTimer.getActiveTimer().getName(), timer.getName())) {
                        simpleTimer.setActiveTimer(null);
                    }
                    commandSender.sendMessage("Removed timer: " + timer.getName());
                    return true;
                }
                case "create" -> {
                    String timerName = strings[1].trim();

                    if (timerName.isEmpty()) {
                        commandSender.sendMessage("The timer name is not allowed to be empty.");
                        return true;
                    }
                    if (timerName.length() > 20) {
                        commandSender.sendMessage("The name can only contain up to 20 characters.");
                        return true;
                    }
                    if (!timerName.matches("[a-zA-Z0-9_-]+")) {
                        commandSender.sendMessage("The name can only contain letters, numbers, underscores, and dashes.");
                        return true;
                    }
                    if (simpleTimer.getTimerManager().getTimer(timerName) != null) {
                        commandSender.sendMessage("A timer with this name already exists.");
                        return true;
                    }

                    Timer newTimer = new Timer(timerName, 0L, false);
                    simpleTimer.getTimerManager().addTimer(newTimer);
                    commandSender.sendMessage(Component.text("Added timer: " + newTimer.getName())
                            .hoverEvent(HoverEvent.showText(Component.text("Click to select")))
                            .clickEvent(ClickEvent.suggestCommand("/timer select " + newTimer.getName())));
                    return true;
                }
            }
        }

        commandSender.sendMessage("Usage: /timer <pause|resume|reset|reload|save|name|list|set|select|remove|create> [time_in_seconds|timer_name]");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> options = List.of("pause", "resume", "reset", "reload", "save", "list", "set", "select", "remove", "name", "create");
        List<String> completions = new ArrayList<>();

        if (strings.length == 1) {
            for (String string : options){
                if (string.toLowerCase().startsWith(strings[0].toLowerCase())) {
                    completions.add(string);
                }
            }
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("select") || strings[0].equalsIgnoreCase("remove")) {
                List<String> timers = SimpleTimer.getPlugin().getTimerManager().getTimerNames();
                for (String string : timers){
                    if (string.toLowerCase().startsWith(strings[1].toLowerCase())) {
                        completions.add(string);
                    }
                }
            }
        }

        return completions;
    }
}
