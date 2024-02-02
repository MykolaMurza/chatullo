# Chatullo - A Minecraft Local and Global Chat Plugin

## Overview

Chatullo is a Minecraft plugin that introduces global and local chat into the game, instead of just one global. The player can freely write to the local chat, which is visible by default within a radius of 200 meters from the player.

This plugin is built with the Paper library and should be compatible with any server running Paper.

## Usage

Use `!` at the start of the message to wrote to the global chat. Don't start your message with `!` if you want to write to the local chat (200 blocks distance).

### Commands

`/chatullo reload` to reload the configuration and localisation files

### Permissions

- `chatullo.reload` to reload config, false by default
- `chatullo.bypass` to bypass global chat requirements, false by default
- `chatullo.global` to chat globally, true by default
- `chatullo.local` to chat locally, true by default

## Installation

To install the plugin, download the latest release and place the `.jar` file into your server's `plugins` folder. Be careful to choose the correct version of Minecraft. Then restart your server, or load the plugin with a plugin manager.

## Configuring

Server administrators have the file `config.yml` in the plugin's directory.
They can change the radius of the local chat distance, local and global format. The file also has keys `join` and `quit`, they are responsible for the message that is displayed in the chat when the player enters or leaves the server. 
```yaml
# Which localisation file should the plugin use
localisation: "en"

# Set the radius of local chat.
radius: 200

# If the player should pay to use global chat
global-pay:
  item:
    enabled: false
    material: "REDSTONE"
    amount: 1
  vault:
    enabled: false
    amount: 10.0

# Both fully support PlaceholderAPI
# Global chat format
global-format: "&8[&cG&8]&r &6%player% &e>&r %message%"

# Local chat format
local-format: "&8[&aL&8]&r &6%player% &e>&r %message%"

mentions:
  enabled: true
  highlight:
    enabled: true
    format: "&e%player%&r"
  sound:
    enabled: true
    name: "block.note_block.pling"
    volume: 0.7
    pitch: 1.0
  actionbar:
    enabled: true

# Message on player join/leave
join: false
quit: false
```

## Contributing

Contributions are welcome! If you want to help improve Chatullo, feel free to fork the repository and submit a pull request.
