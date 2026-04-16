package base;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;

import managers.DriverManager;
import managers.ExtentManager;
import managers.ExtentTestManager;
import utils.BaseUtils;
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
        DriverManager.goToUrl(BaseUtils.getProperty("url"));
        ExtentTestManager.log.info("Setup: launching browser for test: " + method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.SUCCESS) {

            ExtentTestManager.log.pass("Test Passed");

        } else if (result.getStatus() == ITestResult.FAILURE) {

            // Build a unique screenshot name: e.g. "FilterTest.languageFilterTest"
            String screenshotName = result.getInstance().getClass().getSimpleName()
                    + "." + result.getMethod().getMethodName();

            String screenshotPath = BaseUtils.getScreenShotPath(DriverManager.getDriver(), screenshotName);

            ExtentTestManager.log.fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        } else if (result.getStatus() == ITestResult.SKIP) {

            ExtentTestManager.log.skip("Test Skipped");

        }

        ExtentTestManager.log.info("Teardown: closing browser");
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentManager.flushReport();
    }
}
