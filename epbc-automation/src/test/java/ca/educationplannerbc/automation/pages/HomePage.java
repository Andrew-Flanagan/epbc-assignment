package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.educationplannerbc.automation.config.TestConfig;

public class HomePage extends BasePage{
    private final By signInButtonLoc = By.cssSelector("a[href='#']");
    private final By searchBarLoc = By.cssSelector("input[name='search']");
    private final By myListButtonLoc = By.cssSelector("a[href='/my-list']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL);
    }

    public void clickMyListButton() {
        waitAndClick(myListButtonLoc);
    }

    public void clickSignIn() {
        waitAndClick(signInButtonLoc);
    }

    public WebElement getSearchBar() {
        return waitAndGet(searchBarLoc);
    }

    public void searchAndSubmit(String searchTerm) {
        WebElement searchBarEl = waitAndGet(searchBarLoc);
        searchBarEl.clear();
        searchBarEl.sendKeys(searchTerm);
        searchBarEl.sendKeys(Keys.RETURN);
    }
}
