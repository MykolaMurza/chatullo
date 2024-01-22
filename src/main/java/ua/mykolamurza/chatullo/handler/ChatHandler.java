package ua.mykolamurza.chatullo.handler;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.chatullo.Chatullo;

/**
 * @author Mykola Murza
 */
public class ChatHandler {

    private ChatHandler(){}

    private static ChatHandler instance = null;

    public static ChatHandler getInstance() {
        if (instance == null) {
            instance = new ChatHandler();
        }
        return instance;
    }

    private final int radius2 = (int) Math.pow(Chatullo.plugin.getConfig().getInt("radius"), 2);
    private final String globalformat = Chatullo.plugin.getConfig().getString("global-format");
    private final String localformat = Chatullo.plugin.getConfig().getString("local-format");
    public final String join = Chatullo.plugin.getConfig().getString("join");
    public final String quit = Chatullo.plugin.getConfig().getString("quit");

    private final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    public void writeToGlobalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().forEach(recipient ->
                recipient.sendMessage(formatMessage(Type.GLOBAL, player, message)));
    }

    public void writeToLocalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().stream()
                .filter(audience -> audience instanceof Player && isPlayerHearLocalChat(player, (Player) audience)
                        || audience instanceof ConsoleCommandSender)
                .forEach(recipient ->
                        recipient.sendMessage(formatMessage(Type.LOCAL, player, message)));
    }

    private boolean isPlayerHearLocalChat(Player player, Player viewerPlayer) {
        return viewerPlayer.getWorld().equals(player.getWorld()) && viewerPlayer.getLocation().distanceSquared(player.getLocation()) <= radius2;
    }

    public TextComponent formatMessage(Type type, Player player, String message) {
        String formatted = switch (type){
            case GLOBAL -> globalformat;
            case LOCAL -> localformat;
            case OTHER -> message;
        };
        if (Chatullo.papi)
            return LEGACY.deserialize(PlaceholderAPI.setPlaceholders(player, formatted.replace("%player%", player.getName()).replace("%message%", message)));
        else
            return LEGACY.deserialize(formatted.replace("%player%", player.getName()).replace("%message%", message));
    }
}
