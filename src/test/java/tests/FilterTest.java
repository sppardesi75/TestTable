package tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
			assertTrue(language.contains("Java"), "Expected Java but found: " + language);
			
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
			assertTrue(level.contains("Beginner"), "Expected Beginner but found: " + level);
			
		}
		
		
	}
	
	
	@Test
	public void minEnrollmentsTest() {
		
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.selectEnrollment("10000");
		
		List<String> visibleEnrollments = tp.getVisibleColumnValues(5);
		
		for(String minEnrollment: visibleEnrollments) {
			
			
			assertTrue(Integer.parseInt(minEnrollment) >= 10000, "Expected >= 10000 but found: " + minEnrollment);
			System.out.println(minEnrollment);
		}
		
		
	}
	
	@Test
	public void combinedFilters() {
		
		TestTablePage tp = new TestTablePage(wait,driver);
		
		tp.selectLanguage("Python");
		tp.checkUncheckLevel("Intermediate");
		tp.checkUncheckLevel("Advanced");
		tp.selectEnrollment("10000");
		
		
		
		List<WebElement> visibleRows = tp.getAllVisibleTableRows();
		
		
		if(visibleRows != null) {
			for(WebElement visibleRow: visibleRows) {
				
				String language = visibleRow.findElement(By.xpath("./td[3]")).getText();
				String level = visibleRow.findElement(By.xpath("./td[4]")).getText();
				String minEnrollment = visibleRow.findElement(By.xpath("./td[5]")).getText();
				
				System.out.println(language + " "+ level + " " + minEnrollment);
				
				
				assertTrue(language.contains("Python"), "Expected Python but found: " + language);
				assertTrue(level.contains("Beginner"), "Expected Beginner but found: " + level);
				assertTrue(Integer.parseInt(minEnrollment) >= 10000, "Expected >= 10000 but found: " + minEnrollment);
				
				
			}
			
		}
		
		assertTrue(visibleRows != null, "Bug: No rows were visible after applying filters!");
		
		
		
		
		
		
	}
	
	
	@Test
	public void noResultStateTest() {
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.selectLanguage("Python");
		tp.selectEnrollment("50000");
		
		assertTrue(tp.verifyNoDataMessage(),"no-data state does not toggle with filters" );
		
	}
	
	
	@Test
	public void resetButtonVisibilityAndBehaviourTest() {
		
		TestTablePage tp = new TestTablePage(wait,driver);
		tp.selectLanguage("Java");
		
		assertTrue(tp.verifyResetButtonVisibility(), "Reset button not visible");
		
		tp.clickReset();
		
		String lang = tp.getSelectedLanguage();
		List<String> selectedLevel = tp.getSelectedLevels();
		String enrollment = tp.getSelectedMinEnrollment();
		
		
		List<String> levels = new ArrayList<>();
		levels.add("Beginner");
		levels.add("Intermediate");
		levels.add("Advanced");
		
		int counter = 0;
		
		System.out.println(lang);

		
		System.out.println(enrollment);
		assertTrue(lang.contains("Any"), "Language should be Any but is " + lang);
		for(String lvl: levels) {
			
			System.out.println(selectedLevel.get(counter));
			assertTrue(selectedLevel.get(counter).equals(lvl), "Level should be " + lvl + " but it is " + selectedLevel.get(counter));
			
			counter++;
		}
		
		assertTrue(enrollment.equals("any"), "Enrollment should be Any but it is " + enrollment);
		
		
		assertTrue(!tp.verifyResetButtonVisibility(), "Reset button should be hidden but it is visible");
		assertTrue(tp.verifyAllRowVisibility(), "All rows should be visible but are not");
		
	}

}
