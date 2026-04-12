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
	
	
	
	private String languageRadioXpath = "//input[@type='radio' and @value='%s']";
	private String levelCheckboxXpath = "//input[@type='checkbox' and @value='%s']";
	private String tableColXpath = "//table//tr/td[%d]";
	private String minEnrollmentValuePath = "//li[@data-value='%s']";
	
//	private By allLanguageCells = By.xpath("//table[@id='courses_table']//td[3]");
	
//	private By allTableRows = By.xpath("//table[@id='courses_table']/tbody/tr");

	
	
	
	public TestTablePage(WebDriverWait wait, WebDriver driver) {
		this.wait = wait;
		this.driver = driver;
	}
	
	public void selectLanguage(String language) {
		By languageBtn = By.xpath(String.format(languageRadioXpath, language));
        wait.until(ExpectedConditions.elementToBeClickable(languageBtn)).click();
        
       
	}
	
//	public List<WebElement> getAllTableRows(){
//		
//		return driver.findElements(allTableRows);
//		
//	}
	
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
	
	

}
