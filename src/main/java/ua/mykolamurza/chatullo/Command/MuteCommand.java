package ua.mykolamurza.chatullo.Command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MuteCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public MuteCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /mute <player>");
            return true;
        }

        String targetPlayerName = args[0];
        writeMutedPlayerToFile(targetPlayerName);

        sender.sendMessage("Player " + targetPlayerName + " has been muted.");

        return true;
    }

    private void writeMutedPlayerToFile(String playerName) {
        File file = new File(plugin.getDataFolder(), "muted_players.txt");

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(playerName + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}