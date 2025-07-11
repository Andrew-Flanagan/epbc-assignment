package ca.educationplannerbc.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    protected WebElement waitAndGet(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitAndGet(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitAndClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void scrollToAndClick(By locator) {
        WebElement element = waitAndGet(locator);
        new Actions(driver).moveToElement(element).perform();
        waitAndClick(locator);
    }

    protected void scrollToAndClick(WebElement element) {
        waitAndGet(element);
        new Actions(driver).moveToElement(element).perform();
        waitAndClick(element);
    }
}
