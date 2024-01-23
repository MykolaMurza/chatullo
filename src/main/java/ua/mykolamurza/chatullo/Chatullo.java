package ua.mykolamurza.chatullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.chatullo.command.Command;
import ua.mykolamurza.chatullo.command.TabComplete;
import ua.mykolamurza.chatullo.configuration.Config;
import ua.mykolamurza.chatullo.handler.ChatHandler;
import ua.mykolamurza.chatullo.listeners.PlayerChat;
import ua.mykolamurza.chatullo.listeners.PlayerJoin;
import ua.mykolamurza.chatullo.listeners.PlayerQuit;

import java.io.IOException;

/**
 * Local and global chat system. Pay to write to the server.
 *
 * @author Mykola Murza
 * @version Minecraft 1.20.1
 */
public final class Chatullo extends JavaPlugin {

    public static JavaPlugin plugin = null;
    public static boolean papi = false;

    @Override
    public void onEnable() {
        plugin = this;

        //Bukkit.getLogger().info("Start Chatullo.");
        saveDefaultConfig();
        Config.initialize();
        getServer().getPluginManager().registerEvents(new PlayerChat(ChatHandler.getInstance()), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(ChatHandler.getInstance()), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(ChatHandler.getInstance()), this);
        getCommand("chatullo").setExecutor(new Command(ChatHandler.getInstance()));
        getCommand("chatullo").setTabCompleter(new TabComplete());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI not found, functionality will be missing.");
        } else {
            papi = true;
        }
    }
}
