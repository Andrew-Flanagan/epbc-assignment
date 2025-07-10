package ca.educationplannerbc.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestConfig;

public class SearchPage extends BasePage {
    private final By areaOfStudyLoc = By.id("react-select-dropdown-select-5-placeholder");
    private final By addToMyListLoc = By.cssSelector("button[aria-label='Add to My List']");
    private final By filterLoc = By.xpath("//*[@id=\"dropdown-option-search-filter-studyArea-Technology-(IT)\"]/li/label");
    private final By searchResultLoc = By.cssSelector("[class^='Search_result-row']");
    private final By searchButtonLoc = By.cssSelector("button[aria-label='Search']");
    private final By programNameLoc = By.cssSelector("[id^='result-name-']");

    // @FindBy(id="dropdown-option-search-filter-studyArea-Technology-(IT)") WebElement filterOption;
    // @FindBy(css="div[class='dropdown-select__option']") WebElement filterOption;
    // private final By filter = By.id("dropdown-option-search-filter-studyArea-Technology-(IT)");

    // aria-expanded="true"
    // dropdown-select__menu
    // class = dropdown-select__option
    // <label> Technology (IT) </label>

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "search/");
    }

    public void filterByAreaOfStudy() {
        waitAndClick(areaOfStudyLoc);
        scrollToAndClick(filterLoc);
        // due to current bug(?), have to press search button after applying filter
        waitAndClick(searchButtonLoc);
    }

    // don't really like how this is being done
    public String addFirstResultToMyList() {
        // this finds the results from prior to filtering
        waitAndGet(searchResultLoc);
        List<WebElement> searchRow = driver.findElements(searchResultLoc);
        if (!searchRow.isEmpty()) {
            wait.until(ExpectedConditions.stalenessOf(searchRow.get(0)));
        }

        // we then find the results after filtering
        List<WebElement> newSearchRow = driver.findElements(searchResultLoc);
        WebElement firstResult = wait.until(ExpectedConditions.visibilityOf(newSearchRow.get(0)));

        String programNameText = firstResult.findElement(programNameLoc).getText();

        scrollToAndClick(addToMyListLoc);
        return programNameText;
    }

    public String getProgramName() {
        List<WebElement> programResults = driver.findElements(searchResultLoc);
        WebElement firstResult = programResults.get(0);
        WebElement program = firstResult.findElement(programNameLoc);
        return program.getText();
    }
}
