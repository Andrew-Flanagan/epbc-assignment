package ca.educationplannerbc.automation.tests;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import ca.educationplannerbc.automation.config.TestData;
import ca.educationplannerbc.automation.pages.HomePage;
import ca.educationplannerbc.automation.pages.MyListPage;
import ca.educationplannerbc.automation.pages.SearchPage;
import ca.educationplannerbc.automation.pages.SignInPage;

public class AddToMyListTests {
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

    @Disabled
    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    @DisplayName("New user can successfully add programs from search to My List on mobile and desktop")
    public void addProgramsToMyList(boolean isMobile) {
        if (isMobile) { driver.manage().window().setSize(new Dimension(390, 844)); } // iPhone 12 pro dimensions
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

    @Test
    @Disabled
    public void checkHomeButtons() {
        homePage = new HomePage(driver, false);
        homePage.open();
        Map<String, String> buttonMap = Map.ofEntries(
            entry("Plan", "https://stg-www.educationplannerbc.ca/go/plan"),
            entry("Search", "https://stg-www.educationplannerbc.ca/search"),
            entry("Find Your Path", "https://stg-www.educationplannerbc.ca/find-your-path"),
            entry("Apply", "https://stg-apply.educationplannerbc.ca/")
        );

        String buttonText;
        String buttonLink;

        String expectedBtnText;
        String expectedLink;

        for (Map.Entry<String, String> entry : buttonMap.entrySet()) {
            expectedBtnText = entry.getKey();
            expectedLink = entry.getValue();

            List<WebElement> buttons = driver.findElements(By.linkText(expectedBtnText));
            
            assertFalse(buttons.isEmpty(), "Cannot find any button by text: " + expectedBtnText);

            for (WebElement button : buttons) {
                buttonLink = button.getAttribute("href");
                assertEquals(buttonLink, expectedLink);
            }
        }
    }
}
