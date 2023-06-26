package ua.mykolamurza.chatullo.handler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static ua.mykolamurza.chatullo.config.ChatConfig.*;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getJoin;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getQuit;
import static ua.mykolamurza.chatullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class ChatHandler implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.startsWith("!")) {
            if (player.getInventory().getItemInMainHand().getType() == Material.valueOf(getItemToPay())
                    && player.getInventory().getItemInMainHand().getAmount() >= getAmountToPay()) {
                // Global chat
                TextComponent.Builder messageComponent = Component.text()
                        .append(Component.text("[G] ", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(player.getName(), NamedTextColor.YELLOW))
                        .append(Component.text(": "))
                        .append(Component.text(message.substring(1)));
                event.getRecipients().forEach(recipient -> recipient.sendMessage(messageComponent));
                player.getInventory().getItemInMainHand().setAmount(
                        player.getInventory().getItemInMainHand().getAmount() - getAmountToPay());
                Bukkit.getLogger().info(player.getName() + " shouting '" + message + "'");
            } else {
                player.sendMessage(Component.text(String.format(getBundledText("take-redstone"), getAmountToPay()),
                        NamedTextColor.GRAY, TextDecoration.ITALIC));
            }
        } else {
            // Local chat
            event.getRecipients().removeIf(p ->
                    !p.getWorld().equals(player.getWorld()) ||
                            p.getLocation().distance(player.getLocation()) > getLocalChatDistance());
            event.getRecipients().forEach(recipient -> recipient.sendMessage(Component.text()
                    .append(Component.text("[L] ", NamedTextColor.GREEN, TextDecoration.BOLD))
                    .append(Component.text(player.getName(), NamedTextColor.YELLOW))
                    .append(Component.text(": "))
                    .append(Component.text(message))
            ));
            Bukkit.getLogger().info(player.getName() + " saying '" + message + "'");
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (getJoin() == null) {
            event.joinMessage(null);
        } else {
            if (getJoin().contains("%s")) {
                event.joinMessage(Component.text(String.format(getJoin(), event.getPlayer().getName())));
            } else {
                event.joinMessage(Component.text(getJoin()));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (getQuit() == null) {
            event.quitMessage(null);
        } else {
            if (getQuit().contains("%s")) {
                event.quitMessage(Component.text(String.format(getQuit(), event.getPlayer().getName())));
            } else {
                event.quitMessage(Component.text(getQuit()));
            }
        }
    }
}
