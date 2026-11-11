
package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    private static Properties getProperties() {
        if (properties != null) {
            return properties;
        }
        properties = new Properties();
        try {
            InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if (is != null) {
                try (is) { properties.load(is); }
                return properties;
            }
        } catch (IOException e) {
            // fall through
        }
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(fis);
            } catch (IOException e2) {
                throw new RuntimeException("config.properties not found", e2);
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }

    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }
}
