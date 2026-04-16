package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.UiActions;
import utils.WaitUtils;
import managers.ExtentTestManager;

public class TestTablePage {

    //  Locators
    private static final By ALL_LANGUAGE_RADIOS  = By.xpath("//input[@type='radio']");
    private static final By ALL_LEVEL_CHECKBOXES = By.xpath("//input[@type='checkbox']");
    private static final By SELECTED_ENROLLMENT  = By.xpath("//li[@role='option' and @aria-selected='true']");
    private static final By ALL_TABLE_ROWS        = By.xpath("//table[@id='courses_table']/tbody/tr");
    private static final By DROPDOWN_BUTTON       = By.className("dropdown-button");
    private static final By NO_DATA_MESSAGE       = By.id("noData");
    private static final By RESET_BUTTON          = By.id("resetFilters");
    private static final By SORT_DROPDOWN         = By.tagName("select");

    // Parameterized XPath templates
    private static final String LANGUAGE_RADIO_XPATH   = "//input[@type='radio' and @value='%s']";
    private static final String LEVEL_CHECKBOX_XPATH   = "//input[@type='checkbox' and @value='%s']";
    private static final String TABLE_COLUMN_XPATH     = "//table//tr/td[%d]";
    private static final String ENROLLMENT_OPTION_XPATH = "//li[@data-value='%s']";

    //  Filter actions 

    public void selectLanguage(String language) {
        By locator = By.xpath(String.format(LANGUAGE_RADIO_XPATH, language));
        UiActions.click(WaitUtils.waitForClickable(locator), "Language Radio - " + language);
    }

    public void checkUncheckLevel(String level) {
        By locator = By.xpath(String.format(LEVEL_CHECKBOX_XPATH, level));
        UiActions.click(WaitUtils.waitForClickable(locator), "Level Checkbox - " + level);
    }

    public void selectEnrollment(String value) {
        UiActions.click(WaitUtils.waitForClickable(DROPDOWN_BUTTON), "Enrollment Dropdown Button");
        By optionLocator = By.xpath(String.format(ENROLLMENT_OPTION_XPATH, value));
        UiActions.click(WaitUtils.waitForClickable(optionLocator), "Enrollment Option - " + value);
    }

    public void sortBy(String visibleText) {
        WebElement dropdown = WaitUtils.waitForVisible(SORT_DROPDOWN);
        new Select(dropdown).selectByVisibleText(visibleText);
        ExtentTestManager.log.info("Sorted table by: " + visibleText);
    }

    public void clickReset() {
        WebElement resetBtn = WaitUtils.waitForPresence(RESET_BUTTON);
        if (resetBtn.isDisplayed()) {
            UiActions.click(resetBtn, "Reset Button");
        } else {
            ExtentTestManager.log.info("Reset button is not visible, skipping click");
        }
    }

    // Table data queries

    public List<WebElement> getAllRows() {
        return WaitUtils.waitForPresenceOfAll(ALL_TABLE_ROWS);
    }

    public List<WebElement> getAllVisibleTableRows() {
        List<WebElement> visibleRows = new ArrayList<>();
        for (WebElement row : getAllRows()) {
            if (row.isDisplayed()) {
                visibleRows.add(row);
            }
        }
        ExtentTestManager.log.info("Visible table row count: " + visibleRows.size());
        return visibleRows;
    }

    public List<String> getVisibleColumnValues(int colIndex) {
        By colLocator = By.xpath(String.format(TABLE_COLUMN_XPATH, colIndex));
        List<String> values = new ArrayList<>();
        for (WebElement cell : WaitUtils.waitForPresenceOfAll(colLocator)) {
            if (cell.isDisplayed()) {
                values.add(UiActions.getText(cell, "Table Cell - Column " + colIndex));
            }
        }
        return values;
    }

    //  State / verification queries 

    public boolean isNoDataMessageDisplayed() {
        boolean result = WaitUtils.waitForPresence(NO_DATA_MESSAGE).isDisplayed();
        ExtentTestManager.log.info("No data message displayed: " + result);
        return result;
    }

    public boolean isResetButtonVisible() {
        boolean result = WaitUtils.waitForPresence(RESET_BUTTON).isDisplayed();
        ExtentTestManager.log.info("Reset button visible: " + result);
        return result;
    }

    public boolean areAllRowsVisible() {
        for (WebElement row : getAllRows()) {
            if (!row.isDisplayed()) {
                ExtentTestManager.log.info("areAllRowsVisible: false — at least one row is hidden");
                return false;
            }
        }
        ExtentTestManager.log.info("areAllRowsVisible: true — all rows are visible");
        return true;
    }

    /** Returns the currently selected language radio value, or {@code null} if none is selected. */
    public String getSelectedLanguage() {
        for (WebElement radio : WaitUtils.waitForAllVisible(ALL_LANGUAGE_RADIOS)) {
            if (radio.isSelected()) {
                return UiActions.getAttribute(radio, "value", "Selected Language Radio");
            }
        }
        return null;
    }

    /** Returns a list of checked level values, or {@code null} if none are checked. */
    public List<String> getSelectedLevels() {
        List<String> selected = new ArrayList<>();
        for (WebElement checkbox : WaitUtils.waitForAllVisible(ALL_LEVEL_CHECKBOXES)) {
            if (checkbox.isSelected()) {
                selected.add(UiActions.getAttribute(checkbox, "value", "Selected Level Checkbox"));
            }
        }
        return selected.isEmpty() ? null : selected;
    }

    public String getSelectedMinEnrollment() {
        return UiActions.getAttribute(WaitUtils.waitForPresence(SELECTED_ENROLLMENT), "data-value", "Selected Min Enrollment");
    }
}
