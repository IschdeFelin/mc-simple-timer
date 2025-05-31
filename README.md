# Simple Timer

A simple and interactive timer plugin for Minecraft servers (Spigot, Paper, etc.),
supporting multiple named timers with intuitive commands and clickable in-game UI elements.

**Download:** [https://modrinth.com/plugin/simple-timer](https://modrinth.com/plugin/simple-timer)

---

**⚠️ Note:** This version of the plugin (v2.0.0 and above) has been completely rewritten,  
including the documentation. For the old documentation, see here:  
[https://github.com/IschdeFelin/mc-simple-timer/tree/main-v1](https://github.com/IschdeFelin/mc-simple-timer/blob/main-v1/README.md)

---

## Features

- Manage multiple timers with custom names.
- Clickable in-game timer list with hover tooltips and command suggestions.
- Supports pausing, resuming, resetting, saving, and reloading.
- Configurable display with color options and pause messages.
- Timers can run without players online and pause automatically on player death (configurable).

---

## Commands

### `/timer resume`
Resumes the current timer.

### `/timer pause`
Pauses the current timer.

### `/timer reset`
Stops and resets the current timer to 0.

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

If successful, a clickable message appears allowing you to select the timer immediately.

### `/timer select <name>`
Selects the timer with the given name and pauses it by default.

### `/timer name`
Shows the name of the currently selected timer.

### `/timer state <name>`
Shows the current state (time) of the specified timer or the active timer if no name is given.

### `/timer list`
Lists all available timers with their current times.
Each entry is:

- **Clickable:** suggests `/timer select <name>`
- **Hoverable:** shows "Click to select"

Example output:
```plaintext
These timers exist:
- speedrun1 (0s)
- bossfight (1h 23m 51s)
```

### `/timer set <time>`
Sets the current timer to a specific time.

The `<time>` argument can be either a simple number of seconds (e.g. `300`)
or a more detailed format combining days, hours, minutes, and seconds, e.g.:
`13d2s`, `78m12s`, `15h7s`, `12h`, `465s`, `484`

Example:
```bash
/timer set 1d3h17m12s
```

### `/timer add <time>`
Adds the given time to the current timer.

The `<time>` argument accepts the same formats as `/timer set`, for example:
`300`, `78m12s`, `15h7s`, `13d2s`, `465s`

Example:
```bash
/timer add 1h30m
```

### `/timer subtract <time>`
Subtracts the given time from the current timer.

The `<time>` argument accepts the same formats as `/timer set`, for example:
`300`, `78m12s`, `15h7s`, `13d2s`, `465s`

Example:
```bash
/timer subtract 45m20s
```

### `/timer remove <name>`
Removes the specified timer. If no name is given, removes the current timer.
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

| Option                | Description                                                        | Default          |
|-----------------------|--------------------------------------------------------------------|------------------|
| show_pause_message	   | Show a custom message while paused (alternating with time)?        | `true`           |
| pause_message         | The message to display when the timer is paused.                   | `"Timer paused"` |
| color_running         | Timer color when running (`YELLOW`, `WHITE`, etc.)                 | `"YELLOW"`       |
| color_paused          | Timer value color when paused.                                     | `"GOLD"`         |
| color_pause_message   | Color of the pause message.                                        | `"GOLD"`         |
| run_without_players   | If `true`, timer continues running even when no players are online | `false`          |
| pause_on_player_death | If `true`, timer pauses automatically when a player dies           | `false`          |
| auto_select_new_timer | Automatically select the newly created timer                       | `false`          |

### Available Colors

AQUA, BLACK, BLUE, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE,
DARK_RED, GOLD, GRAY, GREEN, LIGHT_PURPLE, RED, WHITE, YELLOW

---

For issues, suggestions, or contributions, feel free to open an issue or pull request on the [GitHub repository](https://github.com/IschdeFelin/mc-simple-timer).