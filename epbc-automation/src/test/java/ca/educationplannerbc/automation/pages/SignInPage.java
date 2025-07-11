package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.educationplannerbc.automation.config.TestData;

public class SignInPage extends BasePage {
    private final By emailBy = By.id("signin-username");
    private final By passwordBy = By.id("signin-password");
    private final By signInBy = By.id("sign-in-button");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestData.BASE_URL + "signin");
    }

    public By getEmailLocator() {
        return emailBy;
    }

    public void signIn(String email, String password) {
        WebElement emailEl = waitAndGet(emailBy);
        emailEl.clear();
        emailEl.sendKeys(email);

        WebElement passwordEl = waitAndGet(passwordBy);
        passwordEl.clear();
        passwordEl.sendKeys(password);

        waitAndClick(signInBy);
    }
}
