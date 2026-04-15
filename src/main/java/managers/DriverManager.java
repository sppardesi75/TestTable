package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ConfigReader;

public class DriverManager {
	
	private static WebDriver driver;
	
	public static void initDriver() {
		boolean notSupported = false;
		
		String browser =  ConfigReader.getProperty("browser");
		switch(browser.toLowerCase()) {
		
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			ExtentTestManager.log.info("Browser not supported");
			notSupported = true;
			break;
		
		}
		if(notSupported) {
			ExtentTestManager.log.info(browser + " brower is launched!!");
		}
		
		driver.manage().window().maximize();
		ExtentTestManager.log.info("Browser window was maximized!!");
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void quitDriver() {
		if (driver != null) {
            driver.quit();
            ExtentTestManager.log.info("Browser window was closed");
		}
	}
	
	public static void goToUrl(String url) {
		
		driver.get(url);
		ExtentTestManager.log.info("URL:" + url + "opened");
	}

}
