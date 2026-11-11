
package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.List;

public class BasePage {

    // 'protected' so child page classes can access the driver if needed
    protected WebDriver driver;

    // 'wait' is used to pause until elements are ready (avoids flaky tests)
    protected WebDriverWait wait;

    // 'actions' gives us advanced mouse/keyboard interactions
    protected Actions actions;

    // Constructor: runs when a new page object is created (e.g. new LoginPage(driver))
    // Sets up the wait timer and actions object for this page
    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Read timeout value from config.properties (default: 10 seconds)
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getTimeout()));
        this.actions = new Actions(driver);
    }

    // Waits for an element to be visible on the page, then returns it
    // Uses ExpectedConditions.visibilityOfElementLocated - waits up to 'timeout' seconds
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits for an element to be clickable (visible AND enabled), then returns it
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Clicks an element - waits for it to be clickable first, then clicks
    public void click(By locator) {
        WebElement element = waitForClickable(locator);
        element.click();
    }

    // Types text into a field - waits for element visibility, selects all, then types
    public void sendKeys(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
    }

    // Gets the visible text content of an element
    public String getText(By locator) {
        return waitForElement(locator).getText();
    }

    // Returns the browser tab's title (from <title> tag in HTML)
    public String get=PageTitle() {
        return driver.getTitle();
    }

    // Returns the current URL in the browser address bar
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Checks if an element is displayed on the page - uses findElements to avoid long waits
    public boolean isDisplayed(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Gets a list of all elements matching a locator (e.g. all product items)
    public List<WebElement> getElements(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    // Waits until the current URL contains a specific text fragment
    // Useful for waiting for page navigation to complete
    public void waitForUrl(String fraction) {
        wait.until(ExpectedConditions.urlContains(fraction));
    }
}
