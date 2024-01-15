package ua.mykolamurza.chatullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.chatullo.handler.ChatHandler;

import java.util.Locale;

import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.setJoinQuit;

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

        Bukkit.getLogger().info("Start Chatullo.");
        saveDefaultConfig();
        setJoinQuit(getConfig().getString("join", null), getConfig().getString("quit", null));
        getServer().getPluginManager().registerEvents(new ChatHandler(), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI not found, functionality will be missing.");
        } else {
            papi = true;
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stop Chatullo.");
    }
}
