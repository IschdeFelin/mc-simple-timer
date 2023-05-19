<h1>Simple Timer</h1>
<p>This is a simple Timer Plugin for Minecraft Servers running on Bukkit, Spigot, Paper, etc.</p>

<h2>What does the Plugin add?</h2>
The Plugin adds a simple timer for all Minecraft Servers supporting Spigot plugins. To use the timer two commands are added:
<ul>
    <li>The <code>/timer</code> command</li>
    <li>The <code>/timerc</code> command</li>
</ul>

<h2>How to use?</h2>
<h3>Start/Resume the timer</h3>
<p>To start the timer, you use the <code>/timer resume</code> command.</p>

<h3>Pause the timer</h3>
<p>To pause the timer, you use the <code>/timer pause</code> command.</p>

<h3>Reset the timer</h3>
<p>To reset the timer, you use the <code>/timer reset</code> command.</p>

<h3>Set the Simple Timer to a specific time</h3>
<p>To set the timer to a specific time you use the <code>/timer set &lt;int:time&gt;</code> command. The time must be given in seconds. To set the timer to 5 minutes, for example, you have to enter <code>/timer set 300</code>.</p>

<h3>Save the timer</h3>
<p>To save the timer for later, you use the <code>/timer save &lt;string:name&gt;</code> command. To save the timer as <i>speedrun</i>, for example, you have to enter <code>/timer save speedrun</code></p>

<h3>Load a timer</h3>
<p>To load a saved timer, you use the <code>/timer load &lt;string:name&gt;</code> command. If you want to load the <i>speedrun</i> timer, use the <code>/timer load speedrun</code> command.</p>

<h3>List the timers</h3>
<p>To list all saved timer, you use the <code>/timer list</code> command.</p>

<h3>Show the status of the timer</h3>
<p>To show the status of a saved timer, you use the <code>/timer show &lt;string:name&gt;</code> command. If you want to show the status of the <i>speedrun</i> timer, use the <code>/timer show speedrun</code></p>

<h3>Reload the config</h3>
<p>If you changed something in the config, use the <code>/timerc reload</code> command to reload the config.</p>

<h2>How to install?</h2>
<p>To install the Simple Timer plugin on your Minecraft server, save the downloaded .jar file in your server's plugins folder and restart your server.</p>

<h2>How to edit the config?</h2>
<p>The config.yml file is in the SimpleTimer subfolder of the plugins folder on your server. To edit something, you open the file. To change the colors of the timer, you can change the entries under colors. The allowed colors are listed below. The entry <code>timer</code> represents the color of the timer when it is running and <code>timer-pause</code> when it is paused.</p>
<p>Allowed colors:</p>
<ul>
    <li>AQUA</li>
    <li>BLACK</li>
    <li>BLUE</li>
    <li>GRAY</li>
    <li>GREEN</li>
    <li>GOLD</li>
    <li>ITALIC</li>
    <li>LIGHT_PURPLE</li>
    <li>RED</li>
    <li>WHITE</li>
    <li>YELLOW</li>
    <li>DARK_AQUA</li>
    <li>DARK_BLUE</li>
    <li>DARK_GRAY</li>
    <li>DARK_GREEN</li>
    <li>DARK_PURPLE</li>
    <li>DARK_RED</li>
</ul>