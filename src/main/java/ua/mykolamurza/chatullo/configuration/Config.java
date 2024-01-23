package ua.mykolamurza.chatullo.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import ua.mykolamurza.chatullo.Chatullo;

public class Config {

    public static FileConfiguration settings = Chatullo.plugin.getConfig();
    public static FileConfiguration messages = null;

    public static void initialize() {

    }

    public static void reload() {
        Chatullo.plugin.reloadConfig();
        settings = Chatullo.plugin.getConfig();
    }

}
