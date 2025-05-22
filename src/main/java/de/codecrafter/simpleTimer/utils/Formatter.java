package de.codecrafter.simpleTimer.utils;

import java.time.Duration;

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
}
