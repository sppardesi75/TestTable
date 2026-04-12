package tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.TestTablePage;



public class FilterTest extends BaseTest {
	
	
	
	
	@Test
	public void LangaugeFilterTest() {
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.selectLanguage("Java");
		List<String> visibleLanguages = tp.getVisibleColumnValues(3);
		
		for(String language: visibleLanguages) {
			
			System.out.println(language);
			assertTrue(language.contains("Java"));
			
		}
	
	}
	
	@Test
	public void LevelFilterTest() {
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.checkUncheckLevel("Intermediate");
		tp.checkUncheckLevel("Advanced");
		
		List<String> visibleLevels = tp.getVisibleColumnValues(4);
		
		for(String level: visibleLevels) {
			
			System.out.println(level);
			assertTrue(level.contains("Beginner"));
			
		}
		
		
	}
	
	
	@Test
	public void minEnrollmentsTest() {
		
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.selectEnrollment("10000");
		
		
	}
	

}
