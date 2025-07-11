package ca.educationplannerbc.automation.tests;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
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
        homePage.clickSignIn();
        signInPage = new SignInPage(driver);
        signInPage.signIn(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddToMyListFlow() {
        homePage.searchAndSubmit("UBC");
        searchPage = new SearchPage(driver);
        // Search results are filtered
        int resultsBefore = searchPage.getTotalResults();
        searchPage.filterResults("Areas of study", "Technology (IT)");
        int resultsAfter = searchPage.getTotalResults();
        assertTrue(resultsAfter < resultsBefore);

        // Add to My List button text is updated on click
        String AddToListTextBefore = searchPage.getAddToListButton().getText();
        String searchProgramName = searchPage.addFirstResultToMyList();
        String AddToListTextAfter = searchPage.getAddToListButton().getText();
        assertNotEquals(AddToListTextBefore, AddToListTextAfter);

        // List<WebElement> programsAdded = searchPage.addResultsToList(4);
        // for (WebElement e : programsAdded) {
        //     System.out.println(e.getText());
        // }


        // Program names are the same
        homePage.clickMyListButton();
        myListPage = new MyListPage(driver);
        myListPage.clickExplorePrograms();
        String myListProgramName = myListPage.getProgramName();
        assertEquals(searchProgramName, myListProgramName, "Program name added from search results should equal program name in My List page");
        // myListPage.clickListViewButton();
        // WebElement applyButton = myListPage.getListViewApplyButton();
        // assertTrue(applyButton.isDisplayed());
        
        // No saved programs after removing
        // myListPage.clickComparisonView();
        myListPage.removeAllPrograms();
        assertTrue(myListPage.getSavedPrograms().isEmpty());
        // Assert element no longer in DOM
    }
}
