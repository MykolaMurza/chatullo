package ua.mykolamurza.chatullo.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.handler.MessageType;

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
            if (!player.hasPermission("chatullo.global")){
                player.sendMessage(chatHandler.formatMessage(MessageType.OTHER, player, Config.messages.getString("error.permission")));
                return;
            }

            chatHandler.writeToGlobalChat(event, player, message.content().substring(1));
        } else {
            if (!player.hasPermission("chatullo.local")){
                player.sendMessage(chatHandler.formatMessage(MessageType.OTHER, player, Config.messages.getString("error.permission")));
                return;
            }

            chatHandler.writeToLocalChat(event, player, message.content());
        }

        event.setCancelled(true);
    }

}
