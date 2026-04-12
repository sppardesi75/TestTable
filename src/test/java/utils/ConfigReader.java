package utils;



import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    // 1. Declare properties as static
    private static Properties properties;

    // 2. Use a static block to initialize (runs once when class is loaded)
    static {
        try {
            properties = new Properties();
            InputStream is = ConfigReader.class.getClassLoader()
                                         .getResourceAsStream("config.properties");
            properties.load(is);
        } catch (Exception e) {
            System.err.println("Failed to load config properties.");
            e.printStackTrace();
        }
    }

    // 3. Make the method static so you don't need 'new'
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}