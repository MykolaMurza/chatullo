package ua.mykolamurza.chatullo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.handler.MessageType;

public class PlayerQuit implements Listener {

    ChatHandler chatHandler;

    public PlayerQuit(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Config.settings.getBoolean("quit")) {
            String message = Config.messages.getString("quit");
            if (message == null || message.isBlank())
                event.quitMessage(null);
            else
                event.quitMessage(chatHandler.formatMessage(MessageType.OTHER, event.getPlayer(), message));
        }

        if (Config.settings.getBoolean("mentions.enabled")) {
            ChatHandler.getInstance().updateTree();
        }
    }

}
