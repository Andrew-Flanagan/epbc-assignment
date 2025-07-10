package ca.educationplannerbc.automation.pages;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    // private final By signInButton = By.linkText("Sign in");

    @FindBy(css="a[href='#']") WebElement signInButton;     // don't love this selector
    @FindBy(css="input[name='search']") WebElement searchBar;
    // @FindBy(css="input[name='search']") WebElement searchBar;
    @FindBy(css="a[href='/my-list']") WebElement myListButton;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL);
    }

    public void clickMyListButton() {
        myListButton.click();
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    public void searchAndSubmit(String searchTerm) {
        wait.until(ExpectedConditions.visibilityOf(searchBar));
        searchBar.clear();
        searchBar.sendKeys(searchTerm);
        searchBar.sendKeys(Keys.RETURN);
    }

    

    
}
