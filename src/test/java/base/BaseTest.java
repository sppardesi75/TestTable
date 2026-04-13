package base;







import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import managers.DriverManager;
import utils.ConfigReader;
import utils.WaitUtils;

public class BaseTest {
	
 
	
	@BeforeMethod
	public void setup() {
		String url = ConfigReader.getProperty("url");
		
		DriverManager.initDriver();
		
		
		WaitUtils.initWait();
		
		
		DriverManager.goToUrl(url);
		
		System.out.println("Setup: launchiung browser for test");
		
		
		
	}
	
	@AfterMethod
	public void tearDown() {
		
		if(DriverManager.getDriver() != null) {
			DriverManager.quitDriver();
		}
		System.out.println("Teardown: closing browser");
		
	}

}
