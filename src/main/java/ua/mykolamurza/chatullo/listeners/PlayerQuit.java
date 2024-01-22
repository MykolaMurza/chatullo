package ua.mykolamurza.chatullo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.handler.Type;

public class PlayerQuit implements Listener {

    ChatHandler chatHandler;

    public PlayerQuit(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (chatHandler.quit == null) {
            event.quitMessage(null);
        } else {
            event.quitMessage(chatHandler.formatMessage(Type.OTHER, event.getPlayer(), chatHandler.quit));
        }
    }

}
