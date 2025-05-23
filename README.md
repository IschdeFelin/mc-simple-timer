# Simple Timer

A simple and interactive timer plugin for Minecraft servers (Spigot, Paper, etc.),
supporting multiple named timers with intuitive commands and clickable in-game UI elements.

**Download:** [https://modrinth.com/plugin/simple-timer](https://modrinth.com/plugin/simple-timer)

---

## Features

- Manage multiple timers with custom names.
- Clickable in-game timer list with hover tooltips and command suggestions.
- Supports pausing, resuming, resetting, saving, and reloading.
- Configurable display with color options and pause messages.

---

## Commands

### `/timer create <name>`
Creates a new timer with the given name.
Timer names must be:

- Not empty
- ≤ 20 characters
- Contain only letters, numbers, _ or -

Example:
```bash
/timer create speedrun1
```

If successful, a clickable message will appear to select it immediately.

### `/timer select <name>`
Selects the timer with the given name and pauses it by default.

### `/timer name`
Shows the name of the currently selected timer.

### `/timer list`
Lists all available timers with their current times.
Each entry is:

- **Clickable:** suggests `/timer select <name>`
- **Hoverable:** shows "Click to select"

Example output:
```
These timers exist:
- speedrun1 (0s)
- bossfight (1h 23m 51s)
```

### `/timer set <seconds>`
Sets the current timer to a specific time (in seconds).

Example:
```bash
/timer set 300
```

### `/timer pause`
Pauses the current timer.

### `/timer resume`
Resumes the current timer.

### `/timer reset`
Stops and resets the current timer to 0.

### `/timer remove <name>`
Removes the specified timer.
If the currently active timer is removed, no timer remains selected.

### `/timer reload`
Reloads the configuration file from disk.

### `/timer save`
Manually saves all timer data to disk.
Otherwise, the data is saved with every server shutdown.

---

## Installation

1) Download the `.jar` file from Modrinth.
2) Place it in your server's `/plugins` folder.
3) Restart the server.

---

## Configuration

Located at:
```
/plugins/SimpleTimer/config.yml
```

### Options

| Option            | Description                                                 |
|-------------------|-------------------------------------------------------------|
| show              | Show timer as ActionBar? (`true`/`false`)                   |
| showPauseMessage	 | Show a custom message while paused (alternating with time)? |
| pauseMessage      | The message to display when paused.                         |
| colorRunning      | Timer color when running (YELLOW, WHITE, etc.)              |
| colorPaused       | Timer value color when paused.                              |
| colorPauseMessage | Color of the pause message.                                 |

### Available Colors

AQUA, BLACK, BLUE, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE,
DARK_RED, GOLD, GRAY, GREEN, LIGHT_PURPLE, RED, WHITE, YELLOW

---

For issues, suggestions, or contributions, feel free to open an issue or pull request on the [GitHub repository](https://github.com/IschdeFelin/mc-simple-timer).