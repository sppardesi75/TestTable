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
import pages.TestTablePage;
import utils.CastingUtils;
import utils.SortUtils;

public class FilterTest extends BaseTest {

    // Shared page object — re-created before each test via @BeforeMethod in BaseTest
    private TestTablePage tablePage;

    @BeforeMethod(dependsOnMethods = "setup")
    public void initPage() {
        tablePage = new TestTablePage();
    }

    //  Language filter 

    @Test
    public void languageFilterTest() {
        tablePage.selectLanguage("Java");

        for (String language : tablePage.getVisibleColumnValues(3)) {
            assertTrue(language.contains("Java"), "Expected Java but found: " + language);
        }
    }

    //  Level filter

    @Test
    public void levelFilterTest() {
        tablePage.checkUncheckLevel("Intermediate");
        tablePage.checkUncheckLevel("Advanced");

        for (String level : tablePage.getVisibleColumnValues(4)) {
            assertTrue(level.contains("Beginner"), "Expected Beginner but found: " + level);
        }
    }

    //  Min enrollment filter 

    @Test
    public void minEnrollmentFilterTest() {
        tablePage.selectEnrollment("10000");

        for (String enrollment : tablePage.getVisibleColumnValues(5)) {
            assertTrue(
                Integer.parseInt(enrollment) >= 10000,
                "Expected >= 10000 but found: " + enrollment
            );
        }
    }

    //  Combined filters 

    @Test
    public void combinedFiltersTest() {
        tablePage.selectLanguage("Python");
        tablePage.checkUncheckLevel("Intermediate");
        tablePage.checkUncheckLevel("Advanced");
        tablePage.selectEnrollment("10000");

        List<WebElement> visibleRows = tablePage.getAllVisibleTableRows();
        assertFalse(visibleRows.isEmpty(), "No rows visible after applying combined filters.");

        for (WebElement row : visibleRows) {
            String language   = row.findElement(By.xpath("./td[3]")).getText();
            String level      = row.findElement(By.xpath("./td[4]")).getText();
            String enrollment = row.findElement(By.xpath("./td[5]")).getText();

            assertTrue(language.contains("Python"),          "Expected Python but found: "   + language);
            assertTrue(level.contains("Beginner"),           "Expected Beginner but found: " + level);
            assertTrue(Integer.parseInt(enrollment) >= 10000, "Expected >= 10000 but found: " + enrollment);
        }
    }

    //  No-results state 
    @Test
    public void noResultsStateTest() {
        tablePage.selectLanguage("Python");
        tablePage.selectEnrollment("50000");

        assertTrue(tablePage.isNoDataMessageDisplayed(), "No-data message should be visible.");
    }

    //  Reset button visibility and behaviour 

    @Test
    public void resetButtonVisibilityAndBehaviourTest() {
        tablePage.selectLanguage("Java");
        assertTrue(tablePage.isResetButtonVisible(), "Reset button should be visible after filtering.");

        tablePage.clickReset();

        String       lang     = tablePage.getSelectedLanguage();
        List<String> levels   = tablePage.getSelectedLevels();
        String       enrollment = tablePage.getSelectedMinEnrollment();

        assertTrue(lang.contains("Any"),      "Language should be Any after reset, but was: " + lang);
        assertEquals(levels, List.of("Beginner", "Intermediate", "Advanced"), "All levels should be selected after reset.");
        assertEquals(enrollment, "any",       "Enrollment should be 'any' after reset, but was: " + enrollment);

        assertFalse(tablePage.isResetButtonVisible(), "Reset button should be hidden after reset.");
        assertTrue(tablePage.areAllRowsVisible(),     "All rows should be visible after reset.");
    }

    //  Sort by enrollment 
    @Test
    public void sortByEnrollmentTest() {
        tablePage.sortBy("Enrollments");

        List<Integer> enrollments = CastingUtils.stringListToIntList(tablePage.getVisibleColumnValues(5));

        assertTrue(
            SortUtils.isNumericallyAscending(enrollments),
            "Enrollment column is not sorted ascending: " + enrollments
        );
    }

    //  Sort by course name 

    @Test
    public void sortByCourseNameTest() {
        tablePage.sortBy("Course Name");

        List<String> courseNames = tablePage.getVisibleColumnValues(2);
        assertTrue(
            SortUtils.isAlphabeticallyAscending(courseNames),
            "Course name column is not sorted alphabetically: " + courseNames
        );

        // Verify sort is preserved after applying a level filter
        tablePage.checkUncheckLevel("Beginner");
        courseNames = tablePage.getVisibleColumnValues(2);
        assertTrue(
            SortUtils.isAlphabeticallyAscending(courseNames),
            "Course name sort broken after level filter: " + courseNames
        );
    }
}
