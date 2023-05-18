package de.gamingfelin.simpletimer.utilitys;

public class TimerFormatter {
    public static String formatTime(int duration) {
        String string = "";
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (duration / 24 / 60 / 60 >= 1) {
            days = duration / 24 / 60 / 60;
            duration = duration - ((duration / 24 / 60 / 60) * 24 * 60 * 60);
        }

        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration = duration - ((duration / 60 / 60) * 60 * 60);
        }

        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration = duration - ((duration / 60) * 60);
        }

        if (duration >= 1) {
            seconds = duration;
        }

        if (days >= 1) {
            string = string + days + "d";
        }

        if (hours >= 1 || days >= 1) {
            string = string + hours + "h";
        }

        if (minutes >= 1 || hours >= 1 || days >= 1) {
            string = string + minutes + "m";
        }

        if (seconds >= 1 || minutes >= 1 || hours >= 1 || days >= 1) {
            string = string + seconds + "s";
        }

        return string;
    }
}
