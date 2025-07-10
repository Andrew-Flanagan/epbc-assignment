package ca.educationplannerbc.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.browsingcontext.Locator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class MyListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By programNames = By.cssSelector("p[class^='MyList_program-name']");

    @FindBy(css="a[href='/my-list/views?tab=0']") WebElement exploreProgramsButtons;
    @FindBy(css="p[class^='MyList_program-name']") WebElement programName;
    @FindBy(css="button[name='List View']") WebElement listViewButton;
    @FindBy(css="button[name='Comparison View']") WebElement comparisonViewButton;

    // @FindBy(xpath="//button[p[text()='List View']]") WebElement listViewButton;
    @FindBy(css="button['Remove from My List']") WebElement removeFromListButton;
    @FindBy(css="a[role='button'][aria-label='Apply now']") Locator applyNowButton;
    private final By applyNow = By.cssSelector("a[role='button'][aria-label='Apply now']");
    @FindBy(css="[aria-label='Click to remove']") WebElement removeProgramButton;

    public MyListPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "my-list");
    }

    public void clickExplorePrograms() {
        wait.until(ExpectedConditions.elementToBeClickable(exploreProgramsButtons));
        new Actions(driver).moveToElement(exploreProgramsButtons).perform();
        exploreProgramsButtons.click();
    }

    public String getProgramName() {
        wait.until(ExpectedConditions.visibilityOf(programName));
        return programName.getText();
    }

    public void clickToRemoveProgram() {
        int oldSize = getProgramNames().size();
        removeProgramButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(programNames, oldSize));
}


    public List<WebElement> getProgramNames() {
        return driver.findElements(programNames);
    }

    public void clickListViewButton() {
        wait.until(ExpectedConditions.elementToBeClickable(comparisonViewButton));
        comparisonViewButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[p[text()='List View']]")));
        wait.until(ExpectedConditions.visibilityOf(listViewButton));
        System.out.println(listViewButton.isEnabled());
        System.out.println(listViewButton.isDisplayed());
        new Actions(driver).moveToElement(listViewButton).perform();
        listViewButton.click();
    }

    public void clickRemoveFromListButton() {
        wait.until(ExpectedConditions.elementToBeClickable(removeFromListButton));
        removeFromListButton.click();
    }

    public List<WebElement> getApplyNowButtons() {
        return driver.findElements(applyNow);
    }

}
