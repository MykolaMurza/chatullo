package ua.mykolamurza.chatullo.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
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
        event.setCancelled(true);
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();

        if (message.content().startsWith("!")) {
            if (!player.hasPermission("chatullo.global") && !player.isOp()){
                player.sendMessage(chatHandler.formatMessage(MessageType.OTHER, player, Config.messages.getString("error.permission")));
                return;
            }

            if (Config.settings.getBoolean("global-pay.item.enabled")) {
                ItemStack item = null;
                if (player.getInventory().getItemInMainHand().getType() == Material.valueOf(Config.settings.getString("global-pay.item.material")))
                    item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
                else if (player.getInventory().getItemInOffHand().getType() == Material.valueOf(Config.settings.getString("global-pay.item.material")))
                    item = player.getInventory().getItem(40);

                if (item == null) {
                    player.sendMessage(chatHandler.formatMessage(
                            Config.messages.getString("error.item").replace("%amount%", String.valueOf(Config.settings.getInt("global-pay.item.amount")))));
                    return;
                }

                if (item.getAmount() < Config.settings.getInt("global-pay.item.amount")) {
                    player.sendMessage(chatHandler.formatMessage(
                            Config.messages.getString("error.item").replace("%amount%", String.valueOf(Config.settings.getInt("global-pay.item.amount")))));
                    return;
                }

                item.setAmount(item.getAmount() - Config.settings.getInt("global-pay.item.amount"));
            }

            if (Config.settings.getBoolean("global-pay.vault.enabled")) {
                // TODO: Vault integration
            }

            chatHandler.writeToGlobalChat(event, player, message.content().substring(1));
        } else {
            if (!player.hasPermission("chatullo.local") && !player.isOp()){
                player.sendMessage(chatHandler.formatMessage(MessageType.OTHER, player, Config.messages.getString("error.permission")));
                return;
            }

            chatHandler.writeToLocalChat(event, player, message.content());
        }
    }

}
