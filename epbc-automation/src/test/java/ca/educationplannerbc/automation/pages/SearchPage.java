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
    private final By addToListBy = By.cssSelector("div[class^='Search_result-row'] button[data-status]");

    private final By mobileFiltersBtnby = By.cssSelector("button[aria-label='Filters']");
    private final By mobileFiltersParentBy = By.cssSelector("div[class^='Search_filter-list']");
    private final By mobileSearchBtnBy = By.id("SearchButton");

    private final boolean isMobile;



    public SearchPage(WebDriver driver, boolean isMobile) {
        super(driver);
        this.isMobile = isMobile;
    }

    public void open() {
        driver.get(TestData.BASE_URL + "search/");
    }

    public String getTabProgramsText() {
        return waitAndGet(tabProgramsBy).getText();
    }

    public void filterResults(String filterOption, String filterValue) {
        String oldText = getTabProgramsText();

        WebElement parent;
        if (this.isMobile) {
            clickMobileFilters();
            parent = waitAndGet(mobileFiltersParentBy);
        }
        else {
            parent = waitAndGet(filterContainerBy);
        }
        
        WebElement filterOptionEl = 
            getChildByXPath(
                parent,
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
        if (this.isMobile) {
            scrollToAndClick(waitAndGet(parent));
            scrollToAndClick(mobileSearchBtnBy);
        }
        else {
            waitAndClick(searchBtnBy);
        }

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

    // mobile methods
    public void clickMobileFilters() {
        scrollToAndClick(mobileFiltersBtnby);
    }

    public void mobileFilterResults(String filterOption, String filterValue) {
        String oldText = getTabProgramsText();

        WebElement filterContainerEl = waitAndGet(mobileFiltersParentBy);
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
        scrollToAndClick(waitAndGet(filterContainerEl));

        scrollToAndClick(mobileSearchBtnBy);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(tabProgramsBy, oldText)));
    }
}
