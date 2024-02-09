package ua.mykolamurza.chatullo.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.handler.ChatHandler;

public class PlayerChat implements Listener {
    private final ChatHandler chatHandler;
    private final Economy economy;

    public PlayerChat(ChatHandler chatHandler, Economy economy) {
        this.chatHandler = chatHandler;
        this.economy = economy;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();

        if (message.content().startsWith("!")) {
            writeToTheGlobalChat(event, player, message);
        } else {
            writeToTheLocalChat(event, player, message);
        }
    }

    private void writeToTheGlobalChat(AsyncChatEvent event, Player player, TextComponent message) {
        if (!player.hasPermission("chatullo.global") && !player.isOp()) {
            player.sendMessage(chatHandler.formatMessage(Config.messages.getString("error.permission")));
            return;
        }

        if (!player.hasPermission("chatullo.bypass") && !player.isOp()) {
            if (Config.settings.getBoolean("global-pay.item.enabled")) {
                PlayerInventory inventory = player.getInventory();
                ItemStack item = null;
                if (inventory.getItemInMainHand().getType()
                        == Material.valueOf(Config.settings.getString("global-pay.item.material"))) {
                    item = inventory.getItem(inventory.getHeldItemSlot());
                } else if (inventory.getItemInOffHand().getType()
                        == Material.valueOf(Config.settings.getString("global-pay.item.material"))) {
                    item = inventory.getItem(40);
                }

                if (item == null) {
                    player.sendMessage(chatHandler.formatMessage(
                            Config.messages.getString("error.item").replace("%amount%",
                                    String.valueOf(Config.settings.getInt("global-pay.item.amount")))));
                    return;
                }

                int amount = Config.settings.getInt("global-pay.item.amount");
                if (item.getAmount() < amount) {
                    player.sendMessage(chatHandler.formatMessage(
                            Config.messages.getString("error.item").replace("%amount%", String.valueOf(amount))));
                    return;
                }

                item.setAmount(item.getAmount() - amount);
            }

            if (Config.settings.getBoolean("global-pay.vault.enabled") && economy != null) {
                double amount = Config.settings.getDouble("global-pay.vault.amount");
                if (!economy.has(player, amount)) {
                    player.sendMessage(chatHandler.formatMessage(
                            Config.messages.getString("error.vault").replace("%amount%", String.valueOf(amount))));
                    return;
                }

                economy.withdrawPlayer(player, amount);
            }
        }
        chatHandler.writeToGlobalChat(event, player, message.content().substring(1));
    }

    private void writeToTheLocalChat(AsyncChatEvent event, Player player, TextComponent message) {
        if (!player.hasPermission("chatullo.local") && !player.isOp()) {
            player.sendMessage(chatHandler.formatMessage(Config.messages.getString("error.permission")));
            return;
        }

        chatHandler.writeToLocalChat(event, player, message.content());
    }
}
