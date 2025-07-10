package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.educationplannerbc.automation.config.TestConfig;

public class SignInPage extends BasePage {
    private final By emailInputLoc = By.id("signin-username");
    private final By passwordInputLoc = By.id("signin-password");
    private final By signInButtonLoc = By.id("sign-in-button");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "signin");
    }

    public By getEmailLocator() {
        return emailInputLoc;
    }

    public void signIn(String email, String password) {
        WebElement emailInputEl = waitAndGet(emailInputLoc);
        emailInputEl.clear();
        emailInputEl.sendKeys(email);

        WebElement passwordInputEl = waitAndGet(passwordInputLoc);
        passwordInputEl.clear();
        passwordInputEl.sendKeys(password);

        waitAndClick(signInButtonLoc);
    }
}
