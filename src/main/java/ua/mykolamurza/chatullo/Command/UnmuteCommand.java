package ua.mykolamurza.chatullo.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class UnmuteCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public UnmuteCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /unmute <player>");
            return true;
        }

        String targetPlayerName = args[0];

        if (removeMutedPlayerFromFile(targetPlayerName)) {
            sender.sendMessage("Player " + targetPlayerName + " has been unmuted.");
        } else {
            sender.sendMessage("Player " + targetPlayerName + " is not muted.");
        }

        return true;
    }

    private boolean removeMutedPlayerFromFile(String playerName) {
        File file = new File(plugin.getDataFolder(), "muted_players.txt");
        File tempFile = new File(plugin.getDataFolder(), "temp_muted_players.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().equalsIgnoreCase(playerName)) {
                    writer.write(currentLine + System.lineSeparator());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return tempFile.renameTo(file);
    }
}
