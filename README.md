# Simple Timer

This is a simple timer plugin for Minecraft servers running Bukkit, Spigot, Paper, etc.

**Download:** [https://modrinth.com/plugin/simple-timer](https://modrinth.com/plugin/simple-timer)

---

## What does the Plugin add?

This plugin adds a simple timer for all Minecraft servers supporting Spigot plugins.  
To use the timer, the following command is available:

- `/timer`

## How to use?

### Start/Resume the timer
Use the command:

```bash
/timer resume
```

### Pause the timer
Use the command:

```bash
/timer pause
```

### Reset the timer
Use the command:

```bash
/timer reset
```

### Set the timer to a specific time
Set the timer to a specific time (in seconds) using:

```bash
/timer set <time_in_seconds>
```

For example, to set the timer to 5 minutes (300 seconds):

```bash
/timer set 300
```

### Reload the configuration
If you change something in the config, reload it with:

```bash
/timer reload
```

---

## How to install?

To install the Simple Timer plugin on your Minecraft server, place the downloaded `.jar` file into your server's `plugins` folder and restart your server.

---

## How to edit the config?

The `config.yml` file is located in the `SimpleTimer` subfolder inside the `plugins` folder on your server.  
Open this file to change settings.

### Config options

- **show**: Whether to show the timer as an ActionBar message in-game.
- **showPauseMessage**: When the timer is paused, alternate between showing the timer value and a custom pause message.
- **pauseMessage**: The message to display when the timer is paused.
- **colorRunning**: Text color of the running timer (e.g. "YELLOW", "GREEN", "WHITE").
- **colorPaused**: Text color of the timer value when paused.
- **colorPauseMessage**: Text color of the pause message.

---

### Allowed colors

You can use the following color names for the color settings:

- AQUA
- BLACK
- BLUE
- DARK_AQUA
- DARK_BLUE
- DARK_GRAY
- DARK_GREEN
- DARK_PURPLE
- DARK_RED
- GOLD
- GRAY
- GREEN
- LIGHT_PURPLE
- RED
- WHITE
- YELLOW