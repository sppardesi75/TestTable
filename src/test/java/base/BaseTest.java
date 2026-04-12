package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@BeforeMethod
	public void setup() {
		String url = ConfigReader.getProperty("url");
		
		
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt( ConfigReader.getProperty("waitTime"))));
		
		driver.get(url);
		
		System.out.println("Setup: launchiung browser for test");
		
		
		
	}
	
	@AfterMethod
	public void tearDown() {
		
		if(driver != null) {
			driver.close();
		}
		System.out.println("Teardown: closing browser");
		
	}

}
