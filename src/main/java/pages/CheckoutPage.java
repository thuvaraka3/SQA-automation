
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    // ---- Locators ----

    // Page title (should be "Checkout: Your Information")
    private final By pageTitle = By.cssSelector("span[data-test='title']");

    // First name text input field
    private final By firstNameField = By.id("first-name");

    // Last name text input field
    private final By lastNameField = By.id("last-name");

    // Zip/Postal code text input field
    private final By zipCodeField = By.id("postal-code");

    // Continue button to proceed to order overview
    private final By continueButton = By.id("continue");

    // Cancel button to go back to the cart
    private final By cancelButton = By.id("cancel");

    // Error message that appears when required fields are missing
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Returns the page title
    public String getTitle() {
        return getText(pageTitle);
    }

    // Types into the first name field
    public void enterFirstName(String firstName) {
        sendKeys(firstNameField, firstName);
    }

    // Types into the last name field
    public void enterLastName(String lastName) {
        sendKeys(lastNameField, lastName);
    }

    // Types into the zip/postal code field
    public void enterZipCode(String zipCode) {
        sendKeys(zipCodeField, zipCode);
    }

    // Clicks the Continue button to proceed to the overview page
    public void clickContinue() {
        click(continueButton);
    }

    // Clicks Cancel to go back to the cart page
    public void clickCancel() {
        click(cancelButton);
    }

    // Convenience method: fills all 3 fields and clicks Continue in one call
    public void enterCheckoutInformation(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        clickContinue();
    }

    // Returns the error message text when validation fails
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    // Checks if a validation error message is displayed
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
}
