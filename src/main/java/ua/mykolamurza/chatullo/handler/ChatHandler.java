package ua.mykolamurza.chatullo.handler;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static ua.mykolamurza.chatullo.config.ChatConfig.*;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getJoin;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getQuit;
import static ua.mykolamurza.chatullo.config.LocalizationConfig.getBundledText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Mykola Murza
 */
public class ChatHandler implements Listener {
    private JavaPlugin plugin;

    public ChatHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();

        if (IsPlayerMuted(player.getName())) {
            event.setCancelled(true);
            informMutedPlayer(player);
            return;
        }

        if (message.content().startsWith("!")) {
            if (getAmountToPay() == 0 || (player.getInventory().getItemInMainHand().getAmount() >= getAmountToPay()
                    && player.getInventory().getItemInMainHand().getType() == Material.valueOf(getItemToPay()))) {
                writeToGlobalChat(event, player, message.content().substring(1));
            } else {
                informPlayerToTakeRedStoneBeforeWriteToGlobalChar(player);
            }
        } else {
            writeToLocalChat(event, player, message.content());
        }

        event.setCancelled(true);
    }

    private boolean IsPlayerMuted(String playerName) {
        File file = new File(plugin.getDataFolder(), "muted_players.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().equalsIgnoreCase(playerName)) {
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void writeToGlobalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().forEach(recipient ->
                recipient.sendMessage(formatMessage("[G] ", NamedTextColor.RED, player, message)));
        player.getInventory().getItemInMainHand().setAmount(
                player.getInventory().getItemInMainHand().getAmount() - getAmountToPay());
    }

    private void informPlayerToTakeRedStoneBeforeWriteToGlobalChar(Player player) {
        player.sendMessage(Component.text(String.format(getBundledText("take-redstone"), getAmountToPay()),
                NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    private void informMutedPlayer(Player player) {
        player.sendMessage(Component.text(String.format(getBundledText("mute-message"), getAmountToPay()),
                NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    private void writeToLocalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().stream()
                .filter(audience -> audience instanceof Player && isPlayerHearLocalChat(player, (Player) audience)
                        || audience instanceof ConsoleCommandSender)
                .forEach(recipient ->
                        recipient.sendMessage(formatMessage("[L] ", NamedTextColor.GREEN, player, message)));
    }

    private boolean isPlayerHearLocalChat(Player player, Player viewerPlayer) {
        return !(!viewerPlayer.getWorld().equals(player.getWorld())
                || viewerPlayer.getLocation().distance(player.getLocation()) > getLocalChatDistance());
    }

    private TextComponent.Builder formatMessage(String suffix, NamedTextColor suffixColor,
                                                Player player, String message) {
        return Component.text()
                .append(Component.text(suffix, suffixColor, TextDecoration.BOLD))
                .append(Component.text(player.getName(), NamedTextColor.YELLOW))
                .append(Component.text(": "))
                .append(Component.text(message));
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
