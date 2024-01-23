package ua.mykolamurza.chatullo.handler;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.chatullo.Chatullo;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.mentions.AsciiTree;

import java.util.HashSet;
import java.util.List;

/**
 * @author Mykola Murza
 * @author justADeni
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

    private final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();
    private AsciiTree tree = null;

    public void updateTree() {
        tree = new AsciiTree(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
    }

    public void writeToGlobalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().forEach(recipient ->
                recipient.sendMessage(formatMessage(MessageType.GLOBAL, player, formatMentions(player, recipient, message))));
    }

    public void writeToLocalChat(AsyncChatEvent event, Player player, String message) {
        event.viewers().stream()
                .filter(audience -> audience instanceof Player && isPlayerHearLocalChat(player, (Player) audience)
                        || audience instanceof ConsoleCommandSender)
                .forEach(recipient ->
                        recipient.sendMessage(formatMessage(MessageType.LOCAL, player, formatMentions(player, recipient, message))));
    }

    private boolean isPlayerHearLocalChat(Player player, Player viewerPlayer) {
        return viewerPlayer.getWorld().equals(player.getWorld())
                && viewerPlayer.getLocation().distanceSquared(player.getLocation()) <= square(Config.settings.getInt("radius"));
    }

    private String formatMentions(Player player, Audience recipient, String message) {
        if (recipient instanceof ConsoleCommandSender)
            return message;

        if (recipient == player)
            return message;

        if (!Config.settings.getBoolean("mentions.enabled"))
            return message;

        String formatted = message;

        List<Integer> foundIndexes = tree.findMultiple(formatted);
        HashSet<Integer> mentionedAlready = new HashSet<>();
        if (!foundIndexes.isEmpty()) {
            for (int index : foundIndexes) {
                int start = (short) (index >> 16);
                int length = (short) (index);
                int end = start + length;
                String word = formatted.substring(start, end);

                if (word.equals(player.getName()))
                    continue;

                if (!word.equals(((Player) recipient).getName()))
                    continue;

                if (Config.settings.getBoolean("mentions.highlight.enabled")) {
                    String replaced = Config.settings.getString("mentions.highlight.format").replace("%player%", word);
                    formatted = formatted.substring(0,start) + replaced + formatted.substring(end);
                }

                if (mentionedAlready.contains(word.hashCode()))
                    continue;

                mentionedAlready.add(word.hashCode());

                if (Config.settings.getBoolean("mentions.sound.enabled")) {
                    float volume = (float) Config.settings.getDouble("mentions.sound.volume");
                    float pitch = (float) Config.settings.getDouble("mentions.sound.pitch");
                    String name = Config.settings.getString("mentions.sound.name");
                    Sound sound = Sound.sound(Key.key(name), Sound.Source.MUSIC, volume, pitch);
                    recipient.playSound(sound);
                }
                if (Config.settings.getBoolean("mentions.sound.actionbar")) {
                    recipient.sendActionBar(LEGACY.deserialize(Config.messages.getString("mentions.actionbar")));
                }
            }
        }

        return formatted;
    }

    public TextComponent formatMessage(MessageType type, Player player, String message) {
        String formatted = switch (type){
            case GLOBAL -> Config.settings.getString("global-format");
            case LOCAL -> Config.settings.getString("local-format");
            case OTHER -> message;
        };

        if (Chatullo.papi)
            return LEGACY.deserialize(PlaceholderAPI.setPlaceholders(player, formatted.replace("%player%", player.getName()).replace("%message%", message)));
        else
            return LEGACY.deserialize(formatted.replace("%player%", player.getName()).replace("%message%", message));
    }

    public TextComponent formatMessage(String message) {
        return LEGACY.deserialize(message);
    }

    private static int square(int input){
        return input*input;
    }
}
