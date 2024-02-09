package ua.mykolamurza.chatullo.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.codehaus.plexus.util.FileUtils;
import ua.mykolamurza.chatullo.Chatullo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Config {
    private static final File DATA_FOLDER = Chatullo.plugin.getDataFolder();
    public static FileConfiguration settings = Chatullo.plugin.getConfig();
    public static FileConfiguration messages = null;

    public static void initialize() {
        CodeSource src = Chatullo.class.getProtectionDomain().getCodeSource();
        try (ZipInputStream zip = new ZipInputStream(src.getLocation().openStream())) {
            while (true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null) {
                    break;
                }

                if (!e.getName().contains(".yml") || e.getName().contains("plugin.yml") || e.getName().contains("config.yml")) {
                    continue;
                }

                File file = new File(DATA_FOLDER.getPath() + "/" + e.getName());

                if (!file.exists()) {
                    URL url = Chatullo.plugin.getClass().getResource("/" + e.getName());
                    FileUtils.copyURLToFile(url, file);
                }

                if (messages == null) {
                    if (Objects.equals(settings.getString("localisation"),
                            e.getName().replace(".yml", ""))) {
                        messages = YamlConfiguration.loadConfiguration(file);
                    }
                }
            }
        } catch (IOException e) {
            Chatullo.plugin.getLogger().warning("IOException on initializing Chatullo.");
        }
    }

    public static void reload() {
        Chatullo.plugin.reloadConfig();
        settings = Chatullo.plugin.getConfig();

        for (File file : Objects.requireNonNull(DATA_FOLDER.listFiles())) {
            if (Objects.equals(settings.getString("localisation"),
                    file.getName().replace(".yml", ""))) {
                messages = YamlConfiguration.loadConfiguration(file);
                break;
            }
        }
    }
}
