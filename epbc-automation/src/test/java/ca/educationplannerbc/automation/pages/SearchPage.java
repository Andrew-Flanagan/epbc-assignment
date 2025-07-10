package ca.educationplannerbc.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css="input[id='react-select-dropdown-select-5-placeholder']") WebElement areaOfStudyDropdown;
    private final By aOS = By.id("react-select-dropdown-select-5-placeholder");
    private final By addToMyListButton = By.cssSelector("button[aria-label='Add to My List']");
    // @FindBy(id="dropdown-option-search-filter-studyArea-Technology-(IT)") WebElement filterOption;
    // @FindBy(css="div[class='dropdown-select__option']") WebElement filterOption;
    // private final By filter = By.id("dropdown-option-search-filter-studyArea-Technology-(IT)");
    private final By filter = By.xpath("//*[@id=\"dropdown-option-search-filter-studyArea-Technology-(IT)\"]/li/label");
    @FindBy(css="button[aria-label='Search']") WebElement searchButton;


    // aria-expanded="true"
    // dropdown-select__menu
    // class = dropdown-select__option
    // <label> Technology (IT) </label>

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "search/");
    }

    public void clickAreaOfStudy() {
        wait.until(ExpectedConditions.presenceOfElementLocated(aOS));
        driver.findElement(aOS).click();
        // if(areaOfStudyDropdown.getAttribute("aria-expanded").equals("false")) {
        //     areaOfStudyDropdown.click();
        // }
        WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(filter));

        new Actions(driver).scrollToElement(dropdownOption).perform();
        dropdownOption.click();


        searchButton.click();
        
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        
        // List<WebElement> addToMyListButtons = driver.findElements(addToMyListButton);
        // wait.until(ExpectedConditions.stalenessOf(addToMyListButtons.get(0)));

        List<WebElement> newAddToMyListButtons = driver.findElements(addToMyListButton);
        WebElement firstResult = newAddToMyListButtons.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(firstResult));

        new Actions(driver).scrollToElement(firstResult).perform();
        newAddToMyListButtons.get(0).click();
    }

    public String getProgramName() {
        List<WebElement> programResults = driver.findElements(By.cssSelector("div[class^='Search_result-row']"));
        WebElement firstResult = programResults.get(0);
        WebElement program = firstResult.findElement(By.cssSelector("p[id^=result-name]"));
        return program.getText();


    }
    // Might want to use something like this for an assertion 
    // List<WebElement> addToMyListButtons = driver.findElements(By.cssSelector("button[aria-label='Remove from My List']"));
}
