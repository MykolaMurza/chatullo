# Chatullo - A Minecraft Local and Global Chat Plugin

# Forked by justADeni
Forked this plugin for personal purposes, removed redstone payments, added soft-dependency on PlaceholderAPI,
added local and global formats to config, added legacy color formatting support, removed localization, improved distance calculation.

## Overview

Chatullo is a Minecraft plugin that introduces global and local chat into the game, instead of just one global. The player can freely write to the local chat, which is visible by default within a radius of 200 meters from the player.

This plugin is built with the Paper library and should be compatible with any server running Paper.

## Usage

Use `!` at the start of the message to wrote to the global chat. Don't start your message with `!` if you want to write to the local chat (200 blocks distance).

### Commands

The plugin doesn't have any commands.

## Installation

To install the plugin, download the latest release and place the `.jar` file into your server's `plugins` folder. Be careful to choose the correct version of Minecraft. Then restart your server, or load the plugin with a plugin manager.

## Configuring

Server administrators have the file `config.yml` in the plugin's directory.
They can change the radius of the local chat distance, local and global format. The file also has keys `join` and `quit`, they are responsible for the message that is displayed in the chat when the player enters or leaves the server. 
```
# Set the radius of local chat.
radius: 200

# All of these support placeholders from PlaceholderAPI if installed
# Global chat format
global-format: "&8[&cG&8]&r &8| &6%player% &e>&r %message%"

# Local chat format
local-format: "&8[&aL&8]&r &8| &6%player% &e>&r %message%"

# Write a message that will be displayed when a player joins or leaves.
# Leave the field blank if you don't want the message to appear.
# Write %player% instead of the player's name.
join:
quit:
```

## Contributing

Contributions are welcome! If you want to help improve Chatullo, feel free to fork the repository and submit a pull request.
