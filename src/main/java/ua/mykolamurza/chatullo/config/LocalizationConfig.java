package ua.mykolamurza.chatullo.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationConfig {
    private static Locale locale;

    public static void setSystemLanguage(String language) {
        if (language.equals("ua") || language.equals("uk")) {
            setSystemLocale(new Locale("uk", "UA"));
        } else {
            setSystemLocale(new Locale("en", "UK"));
        }
    }

    public static String getBundledText(String key) {
        return ResourceBundle.getBundle("messages", locale).getString(key);
    }

    private static void setSystemLocale(Locale locale) {
        LocalizationConfig.locale = locale;
    }
}
