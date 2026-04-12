package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestTablePage {
	
	WebDriverWait wait;
	WebDriver driver;
	
	
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

	
	
	
	public TestTablePage(WebDriverWait wait, WebDriver driver) {
		this.wait = wait;
		this.driver = driver;
	}
	
	public void selectLanguage(String language) {
		By languageBtn = By.xpath(String.format(languageRadioXpath, language));
        wait.until(ExpectedConditions.elementToBeClickable(languageBtn)).click();
        
       
	}
	
	public List<WebElement> getAllRows() {
		
		return driver.findElements(allTableRows);
		
	}
	
	public List<WebElement> getAllVisibleTableRows(){
		
		List<WebElement> rows = getAllRows();
		
		
		List<WebElement> visibleRows = new ArrayList<>();
		
		for(WebElement row: rows) {
			
			if(row.isDisplayed()) {
				
				visibleRows.add(row);
				
			}
			
		}
		
		return visibleRows;
		
		
		
		
	}
	
	public List<String> getVisibleColumnValues(int colIndex){
		
		
		By colPath = By.xpath(String.format(tableColXpath, colIndex));
		wait.until(ExpectedConditions.presenceOfElementLocated(colPath));
		
		List<WebElement> rows = driver.findElements(colPath);
		
		List<String> visibleColumnValues = new ArrayList<>();
		
		
		for(WebElement row: rows) {
			
			if(row.isDisplayed()) {
				
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
		
		By checkUncheck = By.xpath(String.format(levelCheckboxXpath, level));
		wait.until(ExpectedConditions.elementToBeClickable(checkUncheck)).click();
		
		
	}
	
	
	public void selectEnrollment(String value) {
		
		By dropdownButtonMinEnroll = By.className("dropdown-button") ;
		By minEnrollmentValue = By.xpath(String.format(minEnrollmentValuePath, value));
		
		WebElement dropdownBtnEle = wait.until(ExpectedConditions.elementToBeClickable(dropdownButtonMinEnroll));
		dropdownBtnEle.click();
		
		WebElement optionEle = wait.until(ExpectedConditions.elementToBeClickable(minEnrollmentValue));
		optionEle.click();
		
		
		
	}
	
	public boolean verifyNoDataMessage() {
		
		By noData = By.id("noData");
		
		WebElement noDataEle = driver.findElement(noData);
		
		System.out.println(noDataEle.getText());
		
		return noDataEle.isDisplayed();
		
		
		
		
		
	}
	
	
	public WebElement getResetButton() {
		
		By resetButton= By.id("resetFilters");
		return driver.findElement(resetButton);
		
	}
	
	public boolean verifyResetButtonVisibility() {
		
	
		WebElement resetBtnEle = getResetButton();
		System.out.println(resetBtnEle.getText());
		return resetBtnEle.isDisplayed();
		
	}
	
	
	public void clickReset() {
		
		getResetButton().click();
		
		
		
		
	}
	
	public String getSelectedLanguage() {
		
		By languages = By.xpath(allLanguagePath);
		
		List<WebElement> langRadioEle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(languages));
		
		for(WebElement radio: langRadioEle) {
			
			if(radio.isSelected()) {
				
				System.out.println(radio.getText());
				return radio.getAttribute("value");
				
				
			}
			
		}
		return null;
		
	}
	
	public List<String> getSelectedLevels() {
		
		
		By level = By.xpath(allLevelPath);
		
		List<WebElement> levelCheckboxEle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(level));
		List<String> selectedLevel = new ArrayList<>();
		for(WebElement checkbox:levelCheckboxEle) {
			
			if(checkbox.isSelected()) {
				
				selectedLevel.add(checkbox.getAttribute("value"));
				
			}
			
		}
		
		if(selectedLevel.isEmpty()) {
			return null;
		}
		
		return selectedLevel;
		
		
		
		
	}
	
	public String getSelectedMinEnrollment() {
		
		By enrollment = By.xpath(selectedEnrolmentPath);
		
		WebElement selectedEnrollmentEle = wait.until(ExpectedConditions.presenceOfElementLocated(enrollment));
		
		return selectedEnrollmentEle.getAttribute("data-value");
		
	}
	
	public boolean verifyAllRowVisibility() {
		
		List<WebElement> allRows = getAllRows();
		
		for(WebElement row: allRows) {
			
			if(!row.isDisplayed()) {
				return false;
				
			}
			
		}
		
		return true;
		
		
	}
	
	
	
	

}
