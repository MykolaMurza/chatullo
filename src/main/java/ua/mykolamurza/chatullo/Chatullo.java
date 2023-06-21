package ua.mykolamurza.chatullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.chatullo.handler.ChatHandler;

import java.util.Locale;

import static ua.mykolamurza.chatullo.config.ChatConfig.setItemAndAmount;
import static ua.mykolamurza.chatullo.config.ChatConfig.setLocalChatDistance;
import static ua.mykolamurza.chatullo.config.JoinQuitMessageConfig.setJoinQuit;
import static ua.mykolamurza.chatullo.config.LocalizationConfig.setSystemLanguage;

/**
 * Local and global chat system. Pay to write to the server.
 *
 * @author Mykola Murza
 * @version Minecraft 1.20.1
 */
public final class Chatullo extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Start Chatullo.");
        saveDefaultConfig();
        setLocalChatDistance(getConfig().getInt("radius", 200));
        Locale.setDefault(Locale.ENGLISH);
        setSystemLanguage(getConfig().getString("language", "en"));
        setJoinQuit(getConfig().getString("join", null),
                getConfig().getString("quit", null));
        setItemAndAmount(getConfig().getString("itemToPay", "REDSTONE"),
                getConfig().getInt("amountToPay", 1));

        getServer().getPluginManager().registerEvents(new ChatHandler(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stop Chatullo.");
    }
}
