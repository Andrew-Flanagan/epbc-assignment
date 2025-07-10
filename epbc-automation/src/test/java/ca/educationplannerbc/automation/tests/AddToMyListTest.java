package ca.educationplannerbc.automation.tests;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import ca.educationplannerbc.automation.config.TestConfig;
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
        homePage.clickSignIn();

        signInPage = new SignInPage(driver);
        signInPage.signIn(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD);

        homePage.searchAndSubmit("UBC");

        searchPage = new SearchPage(driver);
        searchPage.filterByAreaOfStudy();
        String searchProgramName = searchPage.addFirstResultToMyList();

        homePage.clickMyListButton();

        myListPage = new MyListPage(driver);
        myListPage.clickExplorePrograms();
        String myListProgramName = myListPage.getProgramName();

        assertEquals(searchProgramName, myListProgramName, "Program name added from search results should equal program name in My List page");
        myListPage.clickToRemoveProgram();

        List<WebElement> newProgramNames = myListPage.getProgramNames();

        assertTrue(newProgramNames.isEmpty());

        // TODO: fix list view
        // myListPage.clickListViewButton();
        // List<WebElement> applyNowButtons = myListPage.getApplyNowButtons();
        // System.out.println(applyNowButtons.size());
        // myListPage.clickRemoveFromListButton();
        // System.out.println(applyNowButtons.size());
        // List<WebElement> applyNowButtonsNew = myListPage.getApplyNowButtons();
        // System.out.println(applyNowButtonsNew.size());

        

    }

}
