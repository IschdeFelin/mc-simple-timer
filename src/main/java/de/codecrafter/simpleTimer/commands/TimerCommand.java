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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static de.codecrafter.simpleTimer.utils.Formatter.formatTime;
import static de.codecrafter.simpleTimer.utils.Formatter.parseTime;

public class TimerCommand implements TabExecutor {
    private final static String usageString = "Usage: /timer <pause|resume|reset|reload|save|name|list|set|select|remove|create|state|add|subtract> [seconds|timer_name]";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by players.");
            return true;
        }


        if (strings.length == 0) {
            commandSender.sendMessage(usageString);
            return true;
        }

        SimpleTimer simpleTimer = SimpleTimer.getPlugin();
        switch (strings[0].toLowerCase()) {
            case "pause" -> {
                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;

                timer.setRunning(false);
                return true;
            }
            case "resume" -> {
                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;

                timer.setRunning(true);
                return true;
            }
            case "reset" -> {
                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;

                timer.setRunning(false);
                timer.setTime(0L);
                return true;
            }
            case "reload" -> {
                simpleTimer.getTimerConfig().reload();
                commandSender.sendMessage("Configuration reloaded successfully.");
                return true;
            }
            case "save" -> {
                simpleTimer.getTimerManager().saveToFile();
                commandSender.sendMessage("All timers have been saved.");
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
                                Component.text("- ")
                                        .append(Component.text(timer.getName() + " (" + formatTime(timer.getTime()) + ")")
                                        .hoverEvent(HoverEvent.showText(Component.text("Click to select")))
                                        .clickEvent(ClickEvent.suggestCommand("/timer select " + timer.getName())))
                        ).toList()
                );

                commandSender.sendMessage(Component.text("Existing timers:")
                        .append(Component.newline())
                        .append(timersFormatted));
                return true;
            }
            case "name" -> {
                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;

                commandSender.sendMessage("The active timer is: " + timer.getName());
                return true;
            }
            case "state" -> {
                Timer timer;
                if (strings.length == 1) {
                    timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                } else {
                    timer = getTimerOrWarn(simpleTimer, commandSender, strings[1]);
                }
                if (timer == null) return true;

                commandSender.sendMessage("State of timer \"" + timer.getName() + "\" is: " + formatTime(timer.getTime()));
                return true;
            }
            case "remove" -> {
                Timer timer;
                if (strings.length == 1) {
                    timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                } else {
                    timer = getTimerOrWarn(simpleTimer, commandSender, strings[1]);
                }
                if (timer == null) return true;

                simpleTimer.getTimerManager().removeTimer(timer);
                Timer activeTimer = simpleTimer.getActiveTimer();
                if (!(activeTimer == null) && activeTimer.getName().equals(timer.getName())) {
                    simpleTimer.setActiveTimer(null);
                }
                commandSender.sendMessage("Removed timer: " + timer.getName());
                return true;
            }
            case "set" -> {
                if (strings.length < 2) {
                    commandSender.sendMessage("Usage: /timer set <seconds>");
                    return true;
                }

                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;
                timer.setRunning(false);

                try {
                    String joinedArgs = String.join("", Arrays.copyOfRange(strings, 1, strings.length)).replaceAll("\\s+", "");
                    long newState = parseTime(joinedArgs);

                    if (newState <= 0) {
                        commandSender.sendMessage("The time must be greater than 0 seconds.");
                    }

                    timer.setTime(newState);
                } catch (IllegalArgumentException e) {
                    commandSender.sendMessage(e.getMessage());
                }

                commandSender.sendMessage("Timer set to " + formatTime(timer.getTime()) + " seconds.");
                return true;
            }
            case "select" -> {
                if (strings.length < 2) {
                    commandSender.sendMessage("Usage: /timer select <name>");
                    return true;
                }

                Timer timer = getTimerOrWarn(simpleTimer, commandSender, strings[1]);
                if (timer == null) return true;

                simpleTimer.setActiveTimer(timer);
                simpleTimer.getActiveTimer().setRunning(false);
                commandSender.sendMessage("Selected timer: " + timer.getName());
                return true;
            }
            case "create" -> {
                if (strings.length < 2) {
                    commandSender.sendMessage("Usage: /timer create <name>");
                    return true;
                }

                String timerName = strings[1].trim();

                if (timerName.isEmpty()) {
                    commandSender.sendMessage("Timer name cannot be empty.");
                    return true;
                }
                if (timerName.length() > 20) {
                    commandSender.sendMessage("Timer name must not exceed 20 characters.");
                    return true;
                }
                if (!timerName.matches("[a-zA-Z0-9_-]+")) {
                    commandSender.sendMessage("Only letters, numbers, underscores (_) and dashes (-) are allowed.");
                    return true;
                }
                if (simpleTimer.getTimerManager().getTimer(timerName) != null) {
                    commandSender.sendMessage("A timer with that name already exists.");
                    return true;
                }

                Timer newTimer = new Timer(timerName, 0L, false);
                simpleTimer.getTimerManager().addTimer(newTimer);

                if (simpleTimer.getTimerConfig().isAutoSelectNewTimer()) {
                    simpleTimer.setActiveTimer(newTimer);
                    commandSender.sendMessage("Created and selected new timer: " + newTimer.getName());
                    return true;
                }

                commandSender.sendMessage(Component.text("Created timer: ")
                        .append(Component.text(newTimer.getName())
                                .hoverEvent(HoverEvent.showText(Component.text("Click to select")))
                                .clickEvent(ClickEvent.suggestCommand("/timer select " + newTimer.getName()))));
                return true;
            }
            case "add" -> {
                if (strings.length < 2) {
                    commandSender.sendMessage("Usage: /timer add <seconds>");
                    return true;
                }

                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;
                timer.setRunning(false);

                try {
                    String joinedArgs = String.join("", Arrays.copyOfRange(strings, 1, strings.length)).replaceAll("\\s+", "");
                    long addState = parseTime(joinedArgs);

                    if (addState <= 0) {
                        commandSender.sendMessage("The time must be greater than 0 seconds.");
                        return true;
                    }

                     if (!timer.addTime(addState)) {
                         commandSender.sendMessage("Unable to add time: the value is too large and would cause an overflow.");
                         return true;
                     }

                    commandSender.sendMessage("Timer increased by " + formatTime(addState) + ". Current time: " + formatTime(timer.getTime()));
                } catch (IllegalArgumentException e) {
                    commandSender.sendMessage(e.getMessage());
                }

                return true;
            }
            case "subtract" -> {
                if (strings.length < 2) {
                    commandSender.sendMessage("Usage: /timer subtract <seconds>");
                    return true;
                }

                Timer timer = getActiveTimerOrWarn(simpleTimer, commandSender);
                if (timer == null) return true;
                timer.setRunning(false);

                try {
                    String joinedArgs = String.join("", Arrays.copyOfRange(strings, 1, strings.length)).replaceAll("\\s+", "");
                    long subtractState = parseTime(joinedArgs);

                    if (subtractState <= 0) {
                        commandSender.sendMessage("The time must be greater than 0 seconds.");
                        return true;
                    }

                    if (!timer.subtractTime(subtractState)) {
                        commandSender.sendMessage("Unable to subtract time: the value is too large and would cause a negative result.");
                        return true;
                    }

                    commandSender.sendMessage("Timer decreased by " + formatTime(subtractState) + ". Current time: " + formatTime(timer.getTime()));
                } catch (IllegalArgumentException e) {
                    commandSender.sendMessage(e.getMessage());
                }

                return true;
            }
        }

        commandSender.sendMessage(usageString);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> options = List.of("pause", "resume", "reset", "reload", "save", "list", "set", "select", "remove", "name", "create", "state", "add", "subtract");

        if (strings.length == 1) {
            return options.stream()
                    .filter(option -> option.startsWith(strings[0].toLowerCase()))
                    .toList();
        } if (strings.length == 2) {
            if (List.of("select", "remove", "state").contains(strings[0].toLowerCase())) {
                return SimpleTimer.getPlugin().getTimerManager().getTimerNames().stream()
                        .filter(name -> name.startsWith(strings[1].toLowerCase()))
                        .toList();
            }
        }

        return List.of();
    }

    private Timer getTimerOrWarn(SimpleTimer plugin, CommandSender sender, String name) {
        Timer timer = plugin.getTimerManager().getTimer(name);
        if (timer == null) {
            sender.sendMessage("Timer \"" + name + "\" does not exist.");
        }

        return timer;
    }

    private Timer getActiveTimerOrWarn(SimpleTimer plugin, CommandSender sender) {
        Timer timer = plugin.getActiveTimer();
        if (timer == null) {
            sender.sendMessage("No timer selected.");
        }

        return timer;
    }
}
