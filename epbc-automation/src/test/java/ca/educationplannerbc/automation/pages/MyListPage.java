package ca.educationplannerbc.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.educationplannerbc.automation.config.TestData;

public class MyListPage extends BasePage {
    // comparison view locators
    private final By programBy = By.cssSelector("p[class^='MyList_program-name']");
    private final By exploreProgramsBtnBy = By.cssSelector("a[href='/my-list/views?tab=0']");
    private final By comparisonRemoveBtnBy = By.cssSelector("[aria-label='Click to remove']");
    
    // list view locators
    private final By listRemoveBtnBy = By.cssSelector("[aria-label='Remove from My List']");
    
    // shared locators
    private final By comparisonBtnBy = By.cssSelector("[class^='MyList_side-panel'] button[name='Comparison View']");
    private final By listBtnBy = By.cssSelector("[class^='MyList_side-panel'] button[name='List View']");

    // mobile locators
    private final By mobileListViewBy = By.cssSelector("button[name='List View']");

    private final boolean isMobile;


    public MyListPage(WebDriver driver, boolean isMobile) {
        super(driver);
        this.isMobile = isMobile;
    }

    // Comparison view methods
    public List<String> getAllComparisonProgramNames() {
        waitAndGet(programBy);
        List<String> programNames = new ArrayList<>();
        List <WebElement> programNamesEl = driver.findElements(programBy);
        for (WebElement e : programNamesEl) {
            programNames.add(e.getText());
        }

        return programNames;
    }

    public void removeAllComparisonPrograms() {
        waitAndGet(programBy);
        List<WebElement> programs = driver.findElements(programBy);
        for (WebElement e : programs) {
            int oldSize = getNumComparisonPrograms();
            waitAndClick(comparisonRemoveBtnBy);
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(programBy, oldSize));
        }
    }

    public int getNumComparisonPrograms() {
        return driver.findElements(programBy).size();
    }

    // List view methods
    public void removeAllListPrograms() {
        waitAndGet(listRemoveBtnBy);
        List<WebElement> programs = driver.findElements(listRemoveBtnBy);
        for (WebElement e : programs) {
            int oldSize = driver.findElements(listRemoveBtnBy).size();
            scrollToAndClick(listRemoveBtnBy);
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(listRemoveBtnBy, oldSize));
        }
    }

    public int getListNumPrograms() {
        return driver.findElements(listRemoveBtnBy).size();
    }

    // Shared methods
    public void open() {
        driver.get(TestData.BASE_URL + "my-list");
    }

    public void clickComparisonView() {
        waitAndGet(listRemoveBtnBy);
        scrollToAndClick(comparisonBtnBy);
    }

    public void clickListView() {
        if (this.isMobile) {
            mobileClickListView();
        }
        else{
            scrollToAndClick(listBtnBy);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(listRemoveBtnBy));
    }

    public void clickExplorePrograms() {
        scrollToAndClick(exploreProgramsBtnBy);
    }

    // Mobile
    public void mobileClickListView() {
        scrollToAndClick(mobileListViewBy);
    }
}
