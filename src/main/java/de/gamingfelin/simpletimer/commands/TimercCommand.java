package de.gamingfelin.simpletimer.commands;

import de.gamingfelin.simpletimer.SimpleTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TimercCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                SimpleTimer simpleTimer = SimpleTimer.getPlugin();
                try {
                    simpleTimer.reloadConfig();
                    FileConfiguration config = simpleTimer.getConfig();
                    String timerColorString = config.getString("colors.timer");
                    String timerPauseColorString = config.getString("colors.timer-pause");

                    simpleTimer.timerColor = ChatColor.valueOf(timerColorString);
                    simpleTimer.timerPauseColor = ChatColor.valueOf(timerPauseColorString);
                    commandSender.sendMessage("Timer config reloaded!");
                } catch (Exception e) {
                    commandSender.sendMessage("Error reading colors from config!");
                    simpleTimer.getLogger().severe("Error reading colors from config!");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> arguments1 = new ArrayList<>();
        List<String> result = new ArrayList<>();
        arguments1.add("reload");

        if (strings.length == 1) {
            for (String string : arguments1){
                if (string.toLowerCase().startsWith(strings[0].toLowerCase())) {
                    result.add(string);
                }
            }
        } else {
            result.add("");
        }
        return result;
    }
}
