package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ca.educationplannerbc.automation.config.TestConfig;

public class MyListPage {
    private final WebDriver driver;

    @FindBy(css="a[href='/my-list/views?tab=0']") WebElement exploreProgramsButtons;
    @FindBy(css="p[class^='MyList_program-name']") WebElement programName;

    public MyListPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "my-list");
    }

    public void clickExplorePrograms() {
        exploreProgramsButtons.click();
    }

    public String getProgramName() {
        return programName.getText();
    }

}
