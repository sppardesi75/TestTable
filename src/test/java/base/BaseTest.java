package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import managers.DriverManager;
import utils.ConfigReader;
import utils.WaitUtils;

public class BaseTest {

	@BeforeMethod
	public void setup() {
		DriverManager.initDriver();
		WaitUtils.initWait();
		DriverManager.goToUrl(ConfigReader.getProperty("url"));
		System.out.println("Setup: launchiung browser for test");

	}

	@AfterMethod
	public void tearDown() {
		DriverManager.quitDriver();
		System.out.println("Teardown: closing browser");
	}

}
