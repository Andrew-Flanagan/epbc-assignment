package ca.educationplannerbc.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.educationplannerbc.automation.config.TestConfig;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By signInButton = By.cssSelector("a[href='#']");
    private final By searchBar = By.cssSelector("input[name='search']");
    private final By myListButton = By.cssSelector("a[href='/my-list']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void open() {
        driver.get(TestConfig.BASE_URL);
    }

    public void clickMyListButton() {
        wait.until(ExpectedConditions.elementToBeClickable(myListButton));
        driver.findElement(myListButton).click();
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        driver.findElement(signInButton).click();
    }

    public void searchAndSubmit(String searchTerm) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        WebElement searchBarElement = driver.findElement(this.searchBar);
        searchBarElement.clear();
        searchBarElement.sendKeys(searchTerm);
        searchBarElement.sendKeys(Keys.RETURN);
    }
}
