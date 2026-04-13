package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import managers.DriverManager;

public class WaitUtils {

	private static WebDriverWait wait;

	public static void initWait() {

		wait = new WebDriverWait(DriverManager.getDriver(),
				Duration.ofSeconds(Integer.valueOf(ConfigReader.getProperty("waitTime"))));

	}

	public static WebDriverWait getWait() {

		return wait;

	}

	public static WebElement getPresenceOfElement(By locator) {

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	public static List<WebElement> getPresenceOfAllElement(By locator) {

		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	public static WebElement getClickableElement(By locator) {

		return wait.until(ExpectedConditions.elementToBeClickable(locator));

	}

	public static List<WebElement> getAllVisibleElements(By locator) {

		return WaitUtils.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}
	
	public static WebElement getVisibleElement(By locator) {

		return WaitUtils.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

}
