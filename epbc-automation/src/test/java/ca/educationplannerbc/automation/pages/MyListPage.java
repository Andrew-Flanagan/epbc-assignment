package ca.educationplannerbc.automation.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class MyListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css="a[href='/my-list/views?tab=0']") WebElement exploreProgramsButtons;
    @FindBy(css="p[class^='MyList_program-name']") WebElement programName;

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
        return programName.getText();
    }
    
}
