# Chatullo - A Minecraft Local and Global Chat Plugin

## Overview

Chatullo is a Minecraft plugin that introduces global and local chat into the game, instead of just one global. The player can freely write to the local chat, which is visible by default within a radius of 200 meters from the player. To write to the global chat so that the message is visible to all players, the player must pay. The default cost of a message is 2 redstone dust.

This plugin is built with the Paper library and should be compatible with any server running Paper.

## Usage

Use `!` at the start of the message to wrote to the global chat. You have to hold a redstone in main hand. Don't start your message with `!` if you want to write to the local chat (200 blocks distance).

### Commands

The plugin doesn't have any commands.

## Installation

To install the plugin, download the latest release and place the `.jar` file into your server's `plugins` folder. Be careful to choose the correct version of Minecraft. Then restart your server, or load the plugin with a plugin manager.

## Configuring

Server administrators have the file `config.yml` in the plugin's directory. This file provides configurations for plugin language (see [Localization section](#localization)), an item to pay (names as in the Material), a number of items, and the radius of the local chat distance. The file also has keys `join` and `quit`, they are responsible for the message that is displayed in the chat when the player enters or leaves the server. 
```
# Choose a language for plugin messages
# en - English
# uk - Ukrainian
language: 'en'

# Set the radius of local chat.
radius: 200

# Set the item and its amount which a player has to use to write to the global chat.
itemToPay: REDSTONE
amountToPay: 2

# Write a message that will be displayed when a player joins or leaves.
# Leave the field blank if you don't want the message to appear.
# Write %s instead of the player's name.
join:
quit:
```

## Localization

Chatullo supports localization and comes with English and Ukrainian language files by default. You can set `language: 'en'` to use English or set `language: 'uk'` to use Ukrainian. If you want to add new languages - contribute the project! I will be happy to add new languages.

## Contributing

Contributions are welcome! If you want to help improve Chatullo, feel free to fork the repository and submit a pull request.
