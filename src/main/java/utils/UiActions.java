package utils;

import org.openqa.selenium.WebElement;

import managers.ExtentTestManager;

public class UiActions {

	public static void click(WebElement element, String elementName) {
		element.click();
		ExtentTestManager.log.info("Click on " + elementName);
	}

	public static String getText(WebElement element, String elementName) {
		ExtentTestManager.log.info("UI Element Text " + elementName);
		return element.getText();
	}

	public static String getAttribute(WebElement element, String attribute, String elementName) {
		ExtentTestManager.log.info("UI Element attribute " + elementName);
		return element.getAttribute(attribute);
	}

}
