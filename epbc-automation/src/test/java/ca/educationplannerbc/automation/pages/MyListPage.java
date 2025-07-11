package ca.educationplannerbc.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestConfig;

public class MyListPage extends BasePage {
    private final By programBy = By.cssSelector("p[class^='MyList_program-name']");
    private final By exploreProgramsBtnBy = By.cssSelector("a[href='/my-list/views?tab=0']");
    private final By comparisonBtnBy = By.cssSelector("[class^='MyList_side-panel'] button[name='Comparison View']");
    private final By comparisonRemoveBtnBy = By.cssSelector("[aria-label='Click to remove']");

    private final By listBtnBy = By.cssSelector("[class^='MyList_side-panel'] button[name='List View']");
    private final By listRemoveBtnBy = By.cssSelector("[aria-label='Remove from My List']");
    
    private final By applyNowBtnBy = By.cssSelector("a[role='button'][aria-label='Apply now']");

    public MyListPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "my-list");
    }

    public void clickExplorePrograms() {
        scrollToAndClick(exploreProgramsBtnBy);
    }

    public String getProgramName() {
        WebElement programNameEl = waitAndGet(programBy);
        return programNameEl.getText();
    }

    public void removeAllPrograms() {
        List<WebElement> programs = driver.findElements(programBy);
        for (WebElement e : programs) {
            int oldSize = getSavedPrograms().size();
            waitAndClick(comparisonRemoveBtnBy);
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(programBy, oldSize));
        }
    }

    public List<WebElement> getSavedPrograms() {
        return driver.findElements(programBy);
    }

    public void clickListViewButton() {
        scrollToAndClick(listBtnBy);
        wait.until(ExpectedConditions.visibilityOfElementLocated(listRemoveBtnBy));
    }

    public void clickListViewRemoveProgram() {
        waitAndClick(listRemoveBtnBy);
    }

    public void clickRemoveFromListButton() {
        waitAndClick(comparisonRemoveBtnBy);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(applyNowBtnBy));
    }

    public List<WebElement> getApplyNowButtons() {
        return driver.findElements(applyNowBtnBy);
    }

    public WebElement getApplyButton() {
        scrollToAndClick(comparisonBtnBy);
        return waitAndGet(applyNowBtnBy);

    }
    public void clickComparisonView() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(programBy));
    }
}
