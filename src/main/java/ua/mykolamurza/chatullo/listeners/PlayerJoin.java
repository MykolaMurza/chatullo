package ua.mykolamurza.chatullo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.handler.Type;

public class PlayerJoin implements Listener {

    ChatHandler chatHandler;

    public PlayerJoin(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (chatHandler.join == null) {
            event.joinMessage(null);
        } else {
            event.joinMessage(chatHandler.formatMessage(Type.OTHER, event.getPlayer(), chatHandler.join));
        }
    }

}
