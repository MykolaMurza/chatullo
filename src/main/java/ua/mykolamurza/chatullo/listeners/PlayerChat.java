package ua.mykolamurza.chatullo.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ua.mykolamurza.chatullo.handler.ChatHandler;

public class PlayerChat implements Listener {

    ChatHandler chatHandler;

    public PlayerChat(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();

        if (message.content().startsWith("!")) {
            chatHandler.writeToGlobalChat(event, player, message.content().substring(1));
        } else {
            chatHandler.writeToLocalChat(event, player, message.content());
        }

        event.setCancelled(true);
    }

}
