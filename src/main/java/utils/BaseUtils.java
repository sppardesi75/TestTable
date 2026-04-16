package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class BaseUtils {

    // Loaded once when the class is first used
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream is = BaseUtils.class.getClassLoader()
                                            .getResourceAsStream("config.properties");
            properties.load(is);
        } catch (Exception e) {
            System.err.println("Failed to load config properties.");
            e.printStackTrace();
        }
    }

    /** Returns a value from config.properties by key. */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Captures a screenshot of the current browser window, saves it under
     * target/screenshots/, and returns the absolute file path so it can be
     * embedded in the Extent report.
     *
     * @param driver   active WebDriver instance
     * @param pageName used to build a unique file name  (e.g. "FilterTest.languageFilterTest")
     * @return absolute path to the saved PNG file
     * @throws IOException if the file cannot be copied to the target directory
     */
    public static String getScreenShotPath(WebDriver driver, String pageName) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/target/screenshots/image_" + pageName + ".png";
        FileUtils.copyFile(source, new File(path));
        return path;
    }
}
