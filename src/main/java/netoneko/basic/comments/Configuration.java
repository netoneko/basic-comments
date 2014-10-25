package netoneko.basic.comments;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final Properties config = new Properties();

    public static Properties getConfiguration() {
        if (config.isEmpty()) {
            System.out.println("Loading configuration from config.properties");

            try {
                config.load(App.class.getClassLoader().getResourceAsStream("config.properties"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    public static String getProperty(String key) {
        return getConfiguration().getProperty(key);
    }

}
