
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CompletePage extends BasePage {

    // ---- Locators ----

    // Page title (should be "Checkout: Complete!")
    private final By pageTitle = By.cssSelector("span[data-test='title']");

    // The main thank-you heading (e.g. "Thank you for your order!")
    private final By completeHeader = By.cssSelector("h2[data-test='complete-header']");

    // The dispatch/confirmation text below the header
    private final By completeText = By.cssSelector("div[data-test='complete-text']");

    // The "Back Home" button to return to the products page
    private final By backHomeButton = By.id("back-to-products");

    public CompletePage(WebDriver driver) {
        super(driver);
    }

    // Returns the page title ("Checkout: Complete!")
    public String getTitle() {
        return getText(pageTitle);
    }

    // Returns the thank-you header text
    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    // Returns the dispatch/confirmation text
    public String getCompleteText() {
        return getText(completeText);
    }

    // Checks if the thank-you header is visible on the page
    public boolean isCompleteHeaderDisplayed() {
        return isDisplayed(completeHeader);
    }

    // Clicks "Back Home" to navigate back to the products page
    public void clickBackHome() {
        click(backHomeButton);
    }
}
