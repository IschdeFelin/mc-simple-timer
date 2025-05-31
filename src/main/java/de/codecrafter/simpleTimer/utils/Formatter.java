package de.codecrafter.simpleTimer.utils;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
    /**
     * Formats the given time (in seconds) in following format: {@code %d %h %m %s}.
     *
     * @param seconds The time in seconds to format.
     * @return The formatted string.
     */
    public static String formatTime(long seconds) {
        Duration duration = Duration.ofSeconds(seconds);

        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long secs = duration.getSeconds();

        StringBuilder sb = new StringBuilder();

        if (days > 0) sb.append(days).append("d ");
        if (hours > 0 || days > 0) sb.append(hours).append("h ");
        if (minutes > 0 || hours > 0 || days > 0) sb.append(minutes).append("m ");
        if (secs > 0 || minutes > 0 || hours > 0 || days > 0) sb.append(secs).append("s");

        String result = sb.toString().trim();

        return result.isEmpty() ? "0s" : result;
    }

    /**
     * Parses a time string like "1d 3h 20m 15s" or "78m12s" or "484" (seconds)
     * and returns the total time in seconds.
     *
     * @param input the input time string.
     * @return total seconds parsed.
     * @throws IllegalArgumentException if the input cannot be parsed.
     */
    public static long parseTime(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Empty time string.");
        }

        // check if only a number is given
        if (input.matches("^\\d+$")) {
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Number too large.");
            }
        }

        Pattern timePattern = Pattern.compile("(\\d+)([dhmsDHMS])");
        Matcher matcher = timePattern.matcher(input);
        long totalSeconds = 0;
        boolean found = false;

        while (matcher.find()) {
            found = true;

            long value = Long.parseLong(matcher.group(1));
            char unit = Character.toLowerCase(matcher.group(2).charAt(0));

            switch (unit) {
                case 'd' -> totalSeconds += value * 86400;  // 24*60*60
                case 'h' -> totalSeconds += value * 3600;   // 60*60
                case 'm' -> totalSeconds += value * 60;
                case 's' -> totalSeconds += value;
                default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Invalid time format.");
        }

        return totalSeconds;
    }
}
