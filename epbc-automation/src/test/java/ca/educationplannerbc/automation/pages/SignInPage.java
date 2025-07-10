package ca.educationplannerbc.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class SignInPage {
    
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailLocator = By.id("signin-username");
    // private final By passwordInput = By.id("signin-password");
    // private final By signInSubmitButton = By.id("sign-in-button");

    @FindBy(id="signin-username") WebElement emailInput;
    @FindBy(id="signin-password") WebElement passwordInput;
    @FindBy(id="sign-in-button") WebElement signInSubmitButton;


    public SignInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "signin");
    }

    public By getEmailLocator() {
        return emailLocator;
    }

    public void signIn(String email, String password) {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailLocator));

        emailInput.clear();
        emailInput.sendKeys(email);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        signInSubmitButton.click();
    }
}
