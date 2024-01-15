package ua.mykolamurza.chatullo.handler;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ua.mykolamurza.chatullo.Chatullo;

import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getJoin;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.getQuit;

/**
 * @author Mykola Murza
 */
public class ChatHandler implements Listener {

    private static final int radius2 = (int) Math.pow(Chatullo.plugin.getConfig().getInt("radius"), 2);
    private static final String globalformat = Chatullo.plugin.getConfig().getString("global-format");
    private static final String localformat = Chatullo.plugin.getConfig().getString("local-format");
    public static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();

        if (message.content().startsWith("!")) {
            writeToGlobalChat(event, player, message.content().substring(1));
        } else {
            writeToLocalChat(event, player, message.content());
        }

        event.setCancelled(true);
    }

    private void writeToGlobalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().forEach(recipient ->
                recipient.sendMessage(formatMessage(Type.GLOBAL, player, message)));
    }

    private void writeToLocalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().stream()
                .filter(audience -> audience instanceof Player && isPlayerHearLocalChat(player, (Player) audience)
                        || audience instanceof ConsoleCommandSender)
                .forEach(recipient ->
                        recipient.sendMessage(formatMessage(Type.LOCAL, player, message)));
    }

    private boolean isPlayerHearLocalChat(Player player, Player viewerPlayer) {
        return viewerPlayer.getWorld().equals(player.getWorld())
                || viewerPlayer.getLocation().distanceSquared(player.getLocation()) <= radius2;
    }

    private TextComponent formatMessage(Type type, Player player, String message) {
        String formatted = switch (type){
            case GLOBAL -> globalformat;
            case LOCAL -> localformat;
        };
        if (Chatullo.papi)
            return LEGACY.deserialize(PlaceholderAPI.setPlaceholders(player, formatted.replace("%player%", player.getName()).replace("%message%", message)));
        else
            return LEGACY.deserialize(formatted.replace("%player%", player.getName()).replace("%message%", message));
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
