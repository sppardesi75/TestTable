package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;

import utils.UiActions;
import utils.WaitUtils;

public class TestTablePage {

	private String allLanguagePath = "//input[@type='radio']";
	private String allLevelPath = "//input[@type='checkbox']";
	private String selectedEnrolmentPath = "//li[@role='option' and @aria-selected='true']";
	private String languageRadioXpath = "//input[@type='radio' and @value='%s']";
	private String levelCheckboxXpath = "//input[@type='checkbox' and @value='%s']";
	private String tableColXpath = "//table//tr/td[%d]";
	private String minEnrollmentValuePath = "//li[@data-value='%s']";
//	private String noDataPath = "//div[@id='noData']";

//	private By allLanguageCells = By.xpath("//table[@id='courses_table']//td[3]");

	private By allTableRows = By.xpath("//table[@id='courses_table']/tbody/tr");

	public void selectLanguage(String language) {
		final By languageBtnLocator = By.xpath(String.format(languageRadioXpath, language));
		WebElement languageBtn = WaitUtils.getClickableElement(languageBtnLocator);
		UiActions.click(languageBtn);

	}

	public List<WebElement> getAllRows() {

		return WaitUtils.getPresenceOfAllElement(allTableRows);

	}

	public List<WebElement> getAllVisibleTableRows() {

		List<WebElement> rows = getAllRows();

		List<WebElement> visibleRows = new ArrayList<>();

		for (WebElement row : rows) {

			if (row.isDisplayed()) {
				visibleRows.add(row);
			}
		}
		return visibleRows;
	}

	public List<String> getVisibleColumnValues(int colIndex) {

		final By colPath = By.xpath(String.format(tableColXpath, colIndex));

		List<WebElement> rows = WaitUtils.getPresenceOfAllElement(colPath);
		List<String> visibleColumnValues = new ArrayList<>();

		for (WebElement row : rows) {
			if (row.isDisplayed()) {
				visibleColumnValues.add(row.getText());
			}
		}
		return visibleColumnValues;

	}

//	public List<String> getVisibleLanguages() {
//		
//		
//		
//		List<WebElement> languageCells = driver.findElements(allLanguageCells);
//		
//		List<String> languages = new ArrayList<>();
//		
//		for (WebElement cell: languageCells) {
//			
//			if(cell.isDisplayed()) {
//				languages.add(cell.getText());
//			}
//			
//		}
//		
//		return languages;
//		
//		
//		
//	}

	public void checkUncheckLevel(String level) {

		final By checkUncheck = By.xpath(String.format(levelCheckboxXpath, level));
		WebElement checkuncheckBtn = WaitUtils.getClickableElement(checkUncheck);
		UiActions.click(checkuncheckBtn);

	}

	public void selectEnrollment(String value) {

		final By dropdownButtonMinEnroll = By.className("dropdown-button");
		final By minEnrollmentValue = By.xpath(String.format(minEnrollmentValuePath, value));

		WebElement dropdownBtnEle = WaitUtils.getClickableElement(dropdownButtonMinEnroll);
		UiActions.click(dropdownBtnEle);

		WebElement optionEle = WaitUtils.getClickableElement(minEnrollmentValue);
		UiActions.click(optionEle);

	}

	public boolean verifyNoDataMessage() {

		final By noData = By.id("noData");

		WebElement noDataEle = WaitUtils.getPresenceOfElement(noData);

		System.out.println(noDataEle.getText());

		return noDataEle.isDisplayed();

	}

	public WebElement getResetButton() {

		final By resetButton = By.id("resetFilters");
		return WaitUtils.getPresenceOfElement(resetButton);

	}

	public boolean verifyResetButtonVisibility() {

		WebElement resetBtnEle = getResetButton();
		System.out.println(resetBtnEle.getText());
		return resetBtnEle.isDisplayed();

	}

	public void clickReset() {

		if (verifyResetButtonVisibility()) {
			getResetButton().click();
		} else {
			System.out.println("Button not clickable");
		}

	}

	public String getSelectedLanguage() {

		final By languages = By.xpath(allLanguagePath);

		List<WebElement> langRadioEle = WaitUtils.getAllVisibleElements(languages);

		for (WebElement radio : langRadioEle) {

			if (radio.isSelected()) {

				System.out.println(radio.getText());
				return radio.getAttribute("value");

			}

		}
		return null;

	}

	public List<String> getLevels() {

		final By level = By.xpath(allLevelPath);

		List<WebElement> levelCheckboxEle = WaitUtils.getAllVisibleElements(level);
		List<String> selectedLevel = new ArrayList<>();

		for (WebElement checkbox : levelCheckboxEle) {

			if (checkbox.isSelected()) {

				selectedLevel.add(checkbox.getAttribute("value"));

			}

		}

		if (selectedLevel.isEmpty()) {
			return null;
		}

		return selectedLevel;

	}

	public String getSelectedMinEnrollment() {

		final By enrollment = By.xpath(selectedEnrolmentPath);

		WebElement selectedEnrollmentEle = WaitUtils.getPresenceOfElement(enrollment);

		return selectedEnrollmentEle.getAttribute("data-value");

	}

	public boolean verifyAllRowVisibility() {

		List<WebElement> allRows = getAllRows();

		for (WebElement row : allRows) {

			if (!row.isDisplayed()) {
				return false;

			}

		}

		return true;

	}

	public void sortBy(String option) {

		final By sortByLocator = By.tagName("select");

		WebElement sortByEle = WaitUtils.getVisibleElement(sortByLocator);

		Select sortBy = new Select(sortByEle);
		sortBy.selectByVisibleText(option);

	}

}
