package ca.educationplannerbc.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestData;

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
        driver.get(TestData.BASE_URL + "search/");
    }

    public String getTabProgramsText() {
        return waitAndGet(tabProgramsBy).getText();
    }

    public void filterResults(String filterOption, String filterValue) {
        String oldText = getTabProgramsText();

        // sub filter from parent container to match the filter text, more resilient than using current id
        WebElement filterContainerEl = waitAndGet(filterContainerBy);
        WebElement filterOptionEl = 
            getChildByXPath(
                filterContainerEl,
                ".//div[contains(text(), '" + filterOption + "')]",
                "filter");
        waitAndClick(filterOptionEl);

        WebElement filterDropdownEl = waitAndGet(filterDropdownBy);
        WebElement filterValueEl = 
            getChildByXPath(
                filterDropdownEl,
                 ".//label[contains(text(), '" + filterValue + "')]",
                  "dropdown value");
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


    // don't really like how this is being done
    public List<String> addResultsToList(int numToAdd) {
        waitAndGet(searchResultBy);

        List<String> programList = new ArrayList<>();
        List<WebElement> newSearchRow = driver.findElements(searchResultBy);

        for (int i = 0; i < Math.min(numToAdd, newSearchRow.size()); i++) {
            WebElement result = waitAndGet(newSearchRow.get(i));
            programList.add(waitAndGet(result.findElement(programNameBy)).getText());
            String addToListText = waitAndGet((result.findElement(addToListBy))).getText();

            scrollToAndClick(result.findElement(addToListBy));

            WebElement listBtnEl = waitAndGet(result.findElement(addToListBy));            
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(listBtnEl, addToListText)));
        }
        return programList;
    }

    public String getProgramName() {
        List<WebElement> programResults = driver.findElements(searchResultBy);
        WebElement firstResult = programResults.get(0);
        WebElement program = firstResult.findElement(programNameBy);
        return program.getText();
    }
}
