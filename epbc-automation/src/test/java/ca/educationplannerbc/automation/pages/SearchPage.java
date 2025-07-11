package ca.educationplannerbc.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestConfig;

public class SearchPage extends BasePage {
    private final By searchBtnBy = By.cssSelector("button[aria-label='Search']");
    private final By filterContainerBy = By.cssSelector("[class^='Search_search-filters-container']");
    private final By filterDropdownBy = By.cssSelector("[class^='dropdown-select__menu']");
    private final By tabProgramsBy = By.cssSelector("[id^='tab-Programs']");
    private final By searchResultBy = By.cssSelector("[class^='Search_result-row']");
    private final By programNameBy = By.cssSelector("[id^='result-name-']");
    private final By addToListBy = By.cssSelector("button[aria-label$='My List']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "search/");
    }

    public String getTabProgramsText() {
        return waitAndGet(tabProgramsBy).getText();
    }

    public void filterResults(String filterOption, String filterValue) {
        String oldText = getTabProgramsText();

        // sub filter from parent container to match the filter text, more resilient than using current id
        WebElement filterContainerEl = driver.findElement(filterContainerBy);
        WebElement filterOptionEl = filterContainerEl
            .findElement(By.xpath(".//div[contains(text(), '" + filterOption + "')]"));
        waitAndClick(filterOptionEl);

        WebElement filterDropdownEl = driver.findElement(filterDropdownBy);
        WebElement filterValueEl = filterDropdownEl
            .findElement(By.xpath(".//label[contains(text(), '" + filterValue + "')]"));
        scrollToAndClick(filterValueEl);

        // due to current bug(?), have to press search button after applying filter
        waitAndClick(searchBtnBy);
        // waits until the program counter (num results) text has changed after filter is applied
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(tabProgramsBy, oldText)));
    }

    public int getTotalResults() {
        String programTotal = waitAndGet(tabProgramsBy).getText();
        programTotal = programTotal.replaceAll("\\D", "");
        return Integer.parseInt(programTotal);
    }

    public WebElement getAddToListButton() {
        return waitAndGet(addToListBy);
    }

    // don't really like how this is being done
    public String addFirstResultToMyList() {
        waitAndGet(searchResultBy);

        List<WebElement> newSearchRow = driver.findElements(searchResultBy);
        WebElement firstResult = wait.until(ExpectedConditions.visibilityOf(newSearchRow.get(0)));
        String programNameText = firstResult.findElement(programNameBy).getText();

        String addToListText = firstResult.findElement(addToListBy).getText();
        scrollToAndClick(addToListBy);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(addToListBy, addToListText)));
        return programNameText;
    }

    public String getProgramName() {
        List<WebElement> programResults = driver.findElements(searchResultBy);
        WebElement firstResult = programResults.get(0);
        WebElement program = firstResult.findElement(programNameBy);
        return program.getText();
    }
}
