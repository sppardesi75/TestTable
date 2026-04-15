package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import managers.ExtentTestManager;
import pages.TestTablePage;
import utils.CastingUtils;
import utils.SortUtils;

public class FilterTest extends BaseTest {

    private TestTablePage tablePage;

    @BeforeMethod(dependsOnMethods = "setup")
    public void initPage() {
        tablePage = new TestTablePage();
        ExtentTestManager.log.info("TestTablePage initialized");
    }

    // Language filter

    @Test
    public void languageFilterTest() {
        ExtentTestManager.log.info("Applying language filter: Java");
        tablePage.selectLanguage("Java");

        ExtentTestManager.log.info("Verifying all visible rows contain language: Java");
        for (String language : tablePage.getVisibleColumnValues(3)) {
            assertTrue(language.contains("Java"), "Expected Java but found: " + language);
        }
        ExtentTestManager.log.info("Language filter test passed");
    }

    // Level filter

    @Test
    public void levelFilterTest() {
        ExtentTestManager.log.info("Unchecking levels: Intermediate, Advanced");
        tablePage.checkUncheckLevel("Intermediate");
        tablePage.checkUncheckLevel("Advanced");

        ExtentTestManager.log.info("Verifying all visible rows contain level: Beginner");
        for (String level : tablePage.getVisibleColumnValues(4)) {
            assertTrue(level.contains("Beginner"), "Expected Beginner but found: " + level);
        }
        ExtentTestManager.log.info("Level filter test passed");
    }

    // Min enrollment filter

    @Test
    public void minEnrollmentFilterTest() {
        ExtentTestManager.log.info("Applying min enrollment filter: 10000");
        tablePage.selectEnrollment("10000");

        ExtentTestManager.log.info("Verifying all visible rows have enrollment >= 10000");
        for (String enrollment : tablePage.getVisibleColumnValues(5)) {
            assertTrue(
                Integer.parseInt(enrollment) >= 10000,
                "Expected >= 10000 but found: " + enrollment
            );
        }
        ExtentTestManager.log.info("Min enrollment filter test passed");
    }

    // Combined filters

    @Test
    public void combinedFiltersTest() {
        ExtentTestManager.log.info("Applying combined filters: Language=Python, Level=Beginner only, Enrollment>=10000");
        tablePage.selectLanguage("Python");
        tablePage.checkUncheckLevel("Intermediate");
        tablePage.checkUncheckLevel("Advanced");
        tablePage.selectEnrollment("10000");

        List<WebElement> visibleRows = tablePage.getAllVisibleTableRows();
        assertFalse(visibleRows.isEmpty(), "No rows visible after applying combined filters.");
        ExtentTestManager.log.info("Verifying each visible row matches all combined filter criteria");

        for (WebElement row : visibleRows) {
            String language   = row.findElement(By.xpath("./td[3]")).getText();
            String level      = row.findElement(By.xpath("./td[4]")).getText();
            String enrollment = row.findElement(By.xpath("./td[5]")).getText();

            assertTrue(language.contains("Python"),           "Expected Python but found: "   + language);
            assertTrue(level.contains("Beginner"),            "Expected Beginner but found: " + level);
            assertTrue(Integer.parseInt(enrollment) >= 10000, "Expected >= 10000 but found: " + enrollment);
        }
        ExtentTestManager.log.info("Combined filters test passed");
    }

    // No-results state

    @Test
    public void noResultsStateTest() {
        ExtentTestManager.log.info("Applying filters expected to produce no results: Language=Python, Enrollment>=50000");
        tablePage.selectLanguage("Python");
        tablePage.selectEnrollment("50000");

        ExtentTestManager.log.info("Verifying no-data message is displayed");
        assertTrue(tablePage.isNoDataMessageDisplayed(), "No-data message should be visible.");
        ExtentTestManager.log.info("No-results state test passed");
    }

    // Reset button visibility and behaviour

    @Test
    public void resetButtonVisibilityAndBehaviourTest() {
        ExtentTestManager.log.info("Applying language filter to trigger reset button visibility");
        tablePage.selectLanguage("Java");

        ExtentTestManager.log.info("Verifying reset button is visible after filtering");
        assertTrue(tablePage.isResetButtonVisible(), "Reset button should be visible after filtering.");

        ExtentTestManager.log.info("Clicking reset button");
        tablePage.clickReset();

        String       lang       = tablePage.getSelectedLanguage();
        List<String> levels     = tablePage.getSelectedLevels();
        String       enrollment = tablePage.getSelectedMinEnrollment();

        ExtentTestManager.log.info("Verifying filters are cleared after reset");
        assertTrue(lang.contains("Any"),      "Language should be Any after reset, but was: " + lang);
        assertEquals(levels, List.of("Beginner", "Intermediate", "Advanced"), "All levels should be selected after reset.");
        assertEquals(enrollment, "any",       "Enrollment should be 'any' after reset, but was: " + enrollment);

        assertFalse(tablePage.isResetButtonVisible(), "Reset button should be hidden after reset.");
        assertTrue(tablePage.areAllRowsVisible(),     "All rows should be visible after reset.");
        ExtentTestManager.log.info("Reset button visibility and behaviour test passed");
    }

    // Sort by enrollment

    @Test
    public void sortByEnrollmentTest() {
        ExtentTestManager.log.info("Sorting table by: Enrollments");
        tablePage.sortBy("Enrollments");

        List<Integer> enrollments = CastingUtils.stringListToIntList(tablePage.getVisibleColumnValues(5));
        ExtentTestManager.log.info("Verifying enrollment column is sorted in ascending order");
        assertTrue(
            SortUtils.isNumericallyAscending(enrollments),
            "Enrollment column is not sorted ascending: " + enrollments
        );
        ExtentTestManager.log.info("Sort by enrollment test passed");
    }

    // Sort by course name

    @Test
    public void sortByCourseNameTest() {
        ExtentTestManager.log.info("Sorting table by: Course Name");
        tablePage.sortBy("Course Name");

        List<String> courseNames = tablePage.getVisibleColumnValues(2);
        ExtentTestManager.log.info("Verifying course name column is sorted alphabetically");
        assertTrue(
            SortUtils.isAlphabeticallyAscending(courseNames),
            "Course name column is not sorted alphabetically: " + courseNames
        );

        ExtentTestManager.log.info("Applying level filter and verifying sort is preserved");
        tablePage.checkUncheckLevel("Beginner");
        courseNames = tablePage.getVisibleColumnValues(2);
        assertTrue(
            SortUtils.isAlphabeticallyAscending(courseNames),
            "Course name sort broken after level filter: " + courseNames
        );
        ExtentTestManager.log.info("Sort by course name test passed");
    }
}
