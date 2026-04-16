package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import managers.DriverManager;
import managers.ExtentTestManager;

public class WaitUtils {

    private static WebDriverWait wait;

    public static void initWait() {
        int timeout = Integer.parseInt(BaseUtils.getProperty("waitTime"));
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
        ExtentTestManager.log.info("WebDriverWait initialized with timeout: " + timeout + " seconds");
    }

    public static WebElement waitForPresence(By locator) {
        ExtentTestManager.log.info("Waiting for presence of element: " + locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForPresenceOfAll(By locator) {
        ExtentTestManager.log.info("Waiting for presence of all elements: " + locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForClickable(By locator) {
        ExtentTestManager.log.info("Waiting for element to be clickable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static List<WebElement> waitForAllVisible(By locator) {
        ExtentTestManager.log.info("Waiting for all elements to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForVisible(By locator) {
        ExtentTestManager.log.info("Waiting for element to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
