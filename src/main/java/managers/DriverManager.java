package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ConfigReader;

public class DriverManager {
	
	private static WebDriver driver;
	
	public static void initDriver() {
		
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
			System.out.println("Browser not supported");
			break;
		
		}
		driver.manage().window().maximize();
		
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void quitDriver() {
		driver.quit();
	}
	
	public static void goToUrl(String url) {
		
		driver.get(url);
	}

}
