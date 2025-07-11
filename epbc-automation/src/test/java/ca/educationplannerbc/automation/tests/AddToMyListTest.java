package ca.educationplannerbc.automation.tests;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import ca.educationplannerbc.automation.config.TestData;
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
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false }) 
    public void desktopAddToMyListFlow(boolean isMobile) {
        if (isMobile) { driver.manage().window().setSize(new Dimension(390, 844)); }
        homePage = new HomePage(driver, isMobile);
        homePage.open();
        homePage.clickSignIn();
        signInPage = new SignInPage(driver);
        signInPage.signIn(TestData.TEST_USER_EMAIL, TestData.TEST_USER_PASSWORD);

        // Search results are filtered
        homePage.searchAndSubmit(TestData.SEARCH_TERM);
        searchPage = new SearchPage(driver, isMobile);
        int resultsBefore = searchPage.getTotalResults();
        searchPage.filterResults(TestData.FILTER_OPTION, TestData.FILTER_VALUE);
        int resultsAfter = searchPage.getTotalResults();
        assertTrue(resultsAfter < resultsBefore);

        // Add to My List button text is updated on click
        String AddToListTextBefore = searchPage.getAddToListButton().getText();
        List<String> programsAdded = searchPage.addResultsToList(TestData.NUM_PROGRAMS);

        String AddToListTextAfter = searchPage.getAddToListButton().getText();
        assertNotEquals(AddToListTextBefore, AddToListTextAfter);
        
        // All program names displayed on comparision view
        homePage.clickMyListButton();
        myListPage = new MyListPage(driver, isMobile);
        myListPage.clickExplorePrograms();

        List<String> allProgramNames = myListPage.getAllComparisonProgramNames();
        Collections.sort(programsAdded);
        Collections.sort(allProgramNames);
        assertEquals(programsAdded, allProgramNames);

        // List view works, has correct number of programs
        myListPage.clickListView();
        int numProgramsList = myListPage.getListNumPrograms();
        assertEquals(numProgramsList, programsAdded.size());
        
        // No saved programs after removing all
        myListPage.removeAllListPrograms();
        assertEquals(myListPage.getListNumPrograms(), 0 );
    }
}
