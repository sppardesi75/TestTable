package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.UiActions;
import utils.WaitUtils;

public class TestTablePage {

    // ── Locators ─────────────────────────────────────────────────────────────
    private static final By ALL_LANGUAGE_RADIOS  = By.xpath("//input[@type='radio']");
    private static final By ALL_LEVEL_CHECKBOXES = By.xpath("//input[@type='checkbox']");
    private static final By SELECTED_ENROLLMENT  = By.xpath("//li[@role='option' and @aria-selected='true']");
    private static final By ALL_TABLE_ROWS        = By.xpath("//table[@id='courses_table']/tbody/tr");
    private static final By DROPDOWN_BUTTON       = By.className("dropdown-button");
    private static final By NO_DATA_MESSAGE       = By.id("noData");
    private static final By RESET_BUTTON          = By.id("resetFilters");
    private static final By SORT_DROPDOWN         = By.tagName("select");

    // Parameterised XPath templates
    private static final String LANGUAGE_RADIO_XPATH   = "//input[@type='radio' and @value='%s']";
    private static final String LEVEL_CHECKBOX_XPATH   = "//input[@type='checkbox' and @value='%s']";
    private static final String TABLE_COLUMN_XPATH     = "//table//tr/td[%d]";
    private static final String ENROLLMENT_OPTION_XPATH = "//li[@data-value='%s']";

    // ── Filter actions ────────────────────────────────────────────────────────

    public void selectLanguage(String language) {
        By locator = By.xpath(String.format(LANGUAGE_RADIO_XPATH, language));
        UiActions.click(WaitUtils.waitForClickable(locator));
    }

    public void checkUncheckLevel(String level) {
        By locator = By.xpath(String.format(LEVEL_CHECKBOX_XPATH, level));
        UiActions.click(WaitUtils.waitForClickable(locator));
    }

    public void selectEnrollment(String value) {
        UiActions.click(WaitUtils.waitForClickable(DROPDOWN_BUTTON));
        By optionLocator = By.xpath(String.format(ENROLLMENT_OPTION_XPATH, value));
        UiActions.click(WaitUtils.waitForClickable(optionLocator));
    }

    public void sortBy(String visibleText) {
        WebElement dropdown = WaitUtils.waitForVisible(SORT_DROPDOWN);
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public void clickReset() {
        WebElement resetBtn = WaitUtils.waitForPresence(RESET_BUTTON);
        if (resetBtn.isDisplayed()) {
            resetBtn.click();
        }
    }

    // ── Table data queries ────────────────────────────────────────────────────

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
        return visibleRows;
    }

    public List<String> getVisibleColumnValues(int colIndex) {
        By colLocator = By.xpath(String.format(TABLE_COLUMN_XPATH, colIndex));
        List<String> values = new ArrayList<>();
        for (WebElement cell : WaitUtils.waitForPresenceOfAll(colLocator)) {
            if (cell.isDisplayed()) {
                values.add(cell.getText());
            }
        }
        return values;
    }

    // ── State / verification queries ──────────────────────────────────────────

    public boolean isNoDataMessageDisplayed() {
        return WaitUtils.waitForPresence(NO_DATA_MESSAGE).isDisplayed();
    }

    public boolean isResetButtonVisible() {
        return WaitUtils.waitForPresence(RESET_BUTTON).isDisplayed();
    }

    public boolean areAllRowsVisible() {
        for (WebElement row : getAllRows()) {
            if (!row.isDisplayed()) return false;
        }
        return true;
    }

    /** Returns the currently selected language radio value, or {@code null} if none is selected. */
    public String getSelectedLanguage() {
        for (WebElement radio : WaitUtils.waitForAllVisible(ALL_LANGUAGE_RADIOS)) {
            if (radio.isSelected()) {
                return radio.getAttribute("value");
            }
        }
        return null;
    }

    /** Returns a list of checked level values, or {@code null} if none are checked. */
    public List<String> getSelectedLevels() {
        List<String> selected = new ArrayList<>();
        for (WebElement checkbox : WaitUtils.waitForAllVisible(ALL_LEVEL_CHECKBOXES)) {
            if (checkbox.isSelected()) {
                selected.add(checkbox.getAttribute("value"));
            }
        }
        return selected.isEmpty() ? null : selected;
    }

    public String getSelectedMinEnrollment() {
        return WaitUtils.waitForPresence(SELECTED_ENROLLMENT).getAttribute("data-value");
    }
}
