package ca.educationplannerbc.automation.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import ca.educationplannerbc.automation.pages.HomePage;
import ca.educationplannerbc.automation.pages.MyListPage;
import ca.educationplannerbc.automation.pages.SearchPage;
import ca.educationplannerbc.automation.pages.SignInPage;

public class AddToMyListTest {
    private WebDriver driver;
    private HomePage homePage;
    private SignInPage signInPage;
    private SearchPage searchPage;
    private MyListPage myListPage;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePage.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddToMyListFlow() {
        // homePage.clickSignIn();

        // signInPage = new SignInPage(driver);
        // signInPage.signIn("epbctest@test.com", "Password1!");

        homePage.searchAndSubmit("UBC");

        searchPage = new SearchPage(driver);

        searchPage.clickAreaOfStudy();
    }

}
