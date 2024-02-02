package ua.mykolamurza.chatullo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.handler.MessageType;

public class PlayerJoin implements Listener {

    ChatHandler chatHandler;

    public PlayerJoin(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Config.settings.getBoolean("join")) {
            String message = Config.messages.getString("join");
            if (message == null || message.isBlank())
                event.joinMessage(null);
            else
                event.joinMessage(chatHandler.formatMessage(MessageType.OTHER, event.getPlayer(), message));
        }

        if (Config.settings.getBoolean("mentions.enabled")) {
            ChatHandler.getInstance().updateTree();
        }
    }

}
