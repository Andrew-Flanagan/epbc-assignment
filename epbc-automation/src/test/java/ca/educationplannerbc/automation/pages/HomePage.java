package ca.educationplannerbc.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.educationplannerbc.automation.config.TestData;

public class HomePage extends BasePage{
    private final By signInBtnBy = By.cssSelector("[class^='header'] a[href='#']");
    private final By searchInputBy = By.cssSelector("input[name='search']");
    private final By myListBtnBy = By.cssSelector("[class^='header'] a[href='/my-list']");

    private final By mobileMenuBtnBy = By.cssSelector("a[aria-label='Open mobile menu']");
    private final By mobileSignInBtnBy = By.cssSelector("nav[aria-label='Main menu'] a[href='#']");
    private final By mobileMyListBtnBy = By.cssSelector("nav[aria-label='Main menu'] a[href='/my-list']");

    private final boolean isMobile;



    public HomePage(WebDriver driver, boolean isMobile) {
        super(driver);
        this.isMobile = isMobile;
    }

    public void open() {
        driver.get(TestData.BASE_URL);
    }

    public void clickMyListButton() {
        if (this.isMobile) {
            mobileClickNav();
            mobileClickMyList();
        }
        else{
            waitAndClick(myListBtnBy);
        }
    }

    public void clickSignIn() {
        if (this.isMobile) {
            mobileClickSignIn();
        }
        else{
            waitAndClick(signInBtnBy);
        }
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

    public void mobileClickSignIn() {
        waitAndClick(mobileMenuBtnBy);
        waitAndClick(mobileSignInBtnBy);
    }

    public void mobileClickNav() {
        scrollToAndClick(mobileMenuBtnBy);
    }

    public void mobileClickMyList() {
        scrollToAndClick(mobileMyListBtnBy);
    }
}
