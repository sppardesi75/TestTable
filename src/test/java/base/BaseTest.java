package base;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import managers.DriverManager;
import managers.ExtentManager;
import managers.ExtentTestManager;
import utils.ConfigReader;
import utils.WaitUtils;

public class BaseTest {
	
	@BeforeSuite
	public void setupSuite() {
		ExtentManager.initReport();
	}

	@BeforeMethod
	public void setup(Method method) {
		ExtentTestManager.createTest(method.getName());
		DriverManager.initDriver();
		WaitUtils.initWait();
		DriverManager.goToUrl(ConfigReader.getProperty("url"));
		ExtentTestManager.log.info("Setup: launching browser for test: " + method.getName());

	}

	@AfterMethod
	public void tearDown() {
		DriverManager.quitDriver();
		ExtentTestManager.log.info("Teardown: closing browser");
	}
	
	@AfterSuite
	public void tearDownSuite() {
		ExtentManager.flushReport();
	}

}
