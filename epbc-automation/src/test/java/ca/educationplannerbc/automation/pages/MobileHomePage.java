package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.educationplannerbc.automation.config.TestData;

public class MobileHomePage extends BasePage{
    private final By signInBtnBy = By.cssSelector("[class^='header'] a[href='#']");
    private final By searchInputBy = By.cssSelector("input[name='search']");
    private final By myListBtnBy = By.cssSelector("[class^='header'] a[href='/my-list']");

    private final By mobileMenuBtnBy = By.cssSelector("a[href='#']");
    private final By mobileSignInBtnBy = By.cssSelector("nav[href='#']");


    public MobileHomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(TestData.BASE_URL);
    }

    public void clickMyListButton() {
        waitAndClick(myListBtnBy);
    }

    public void clickSignIn() {
        waitAndClick(mobileMenuBtnBy);
        waitAndClick(mobileSignInBtnBy);
    }

    public WebElement getSearchBar() {
        return waitAndGet(searchInputBy);
    }

    public void searchAndSubmit(String searchTerm) {
        WebElement searchInputEl = waitAndGet(searchInputBy);
        searchInputEl.clear();
        searchInputEl.sendKeys(searchTerm);
        searchInputEl.sendKeys(Keys.RETURN);
    }
}
