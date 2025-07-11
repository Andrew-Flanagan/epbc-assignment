package ca.educationplannerbc.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestConfig;

public class MyListPage extends BasePage {
    private final By programNameLoc = By.cssSelector("p[class^='MyList_program-name']");
    private final By exploreProgramsButtonLoc = By.cssSelector("a[href='/my-list/views?tab=0']");
    private final By removeFromListButtonLoc = By.cssSelector("button['Remove from My List']");
    private final By applyNow = By.cssSelector("a[role='button'][aria-label='Apply now']");
    private final By removeProgramButtonLoc = By.cssSelector("[aria-label='Click to remove']");
    private final By listViewButtonLoc = By.cssSelector("[class^='MyList_side-panel'] button[name='List View']");
    private final By ComparisonButtonLoc = By.cssSelector("[class^='MyList_side-panel'] button[name='Comparison View']");
    private final By listViewRemoveButton = By.cssSelector("[aria-label='Remove from My List']");
    private final By listViewApplyButton = By.cssSelector("a[role='button'][aria-label='Apply now']");
    // private final By comparisonViewButtonLoc = By.cssSelector("button[name='Comparison View']");
    // private final By applyNowButton = By.cssSelector("a[role='button'][aria-label='Apply now']");

    public MyListPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "my-list");
    }

    public void clickExplorePrograms() {
        scrollToAndClick(exploreProgramsButtonLoc);
    }

    public String getProgramName() {
        WebElement programNameEl = waitAndGet(programNameLoc);
        return programNameEl.getText();
    }

    public void clickToRemoveProgram() {
        int oldSize = getSavedPrograms().size();
        waitAndClick(removeProgramButtonLoc);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(programNameLoc, oldSize));
    }

    public List<WebElement> getSavedPrograms() {
        return driver.findElements(programNameLoc);
    }

    public void clickListViewButton() {
        scrollToAndClick(listViewButtonLoc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(listViewRemoveButton));
    }

    public void clickListViewRemoveProgram() {
        waitAndClick(listViewRemoveButton);
    }

    public void clickRemoveFromListButton() {
        waitAndClick(removeFromListButtonLoc);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(listViewApplyButton));
    }

    public List<WebElement> getApplyNowButtons() {
        return driver.findElements(applyNow);
    }

    public WebElement getListViewApplyButton() {
        return waitAndGet(listViewApplyButton);
    }

    public void clickComparisonView() {
        scrollToAndClick(ComparisonButtonLoc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(programNameLoc));
    }
}
