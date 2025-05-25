package de.codecrafter.simpleTimer.models;

import de.codecrafter.simpleTimer.SimpleTimer;

/**
 * A timer instance which stores {@code name}, {@code time} and {@code running} states.
 */
public class Timer {
    private String name;
    private long time;
    private boolean running;

    /**
     * Creates a {@code Timer} object.
     *
     * @param name The name of the timer.
     * @param time The current time (in seconds) of the timer.
     * @param running If the timer is currently running.
     */
    public Timer(String name, long time, boolean running) {
        this.name = name;
        this.time = time;
        this.running = running;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean addTime() {
        if (time < Long.MAX_VALUE) {
            this.time++;
            return true;
        } else {
            SimpleTimer.getPlugin().getComponentLogger().error("Timer reached maximum duration!");
            return false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
