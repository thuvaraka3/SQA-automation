
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverviewPage extends BasePage {

    // ---- Locators ----

    // Page title (should be "Checkout: Overview")
    private final By pageTitle = By.cssSelector("span[data-test='title']");

    // The "Finish" button to confirm the purchase
    private final By finishButton = By.id("finish");

    // The "Cancel" button to go back
    private final By cancelButton = By.id("cancel");

    // Each product item listed in the order summary
    private final By cartItems = By.cssSelector(".cart_item");

    // The subtotal label (e.g. "Item total: $29.99")
    private final By itemTotal = By.cssSelector(".summary_subtotal_label");

    // The tax amount label (e.g. "Tax: $2.40")
    private final By tax = By.cssSelector(".summary_tax_label");

    // The final total label (e.g. "Total: $32.39")
    private final By total = By.cssSelector(".summary_total_label");

    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    // Returns the page title
    public String getTitle() {
        return getText(pageTitle);
    }

    // Returns how many items are in this order
    public int getItemCount() {
        return getElements(cartItems).size();
    }

    // Returns the subtotal text (e.g. "Item total: $29.99")
    public String getItemTotal() {
        return getText(itemTotal);
    }

    // Returns the tax text (e.g. "Tax: $2.40")
    public String getTax() {
        return getText(tax);
    }

    // Returns the total text (e.g. "Total: $32.39")
    public String getTotal() {
        return getText(total);
    }

    // Clicks Finish to complete the order
    public void clickFinish() {
        waitForUrl("checkout-step-two.html");
        click(finishButton);
        waitForUrl("checkout-complete.html");
    }

    // Clicks Cancel to go back
    public void clickCancel() {
        click(cancelButton);
    }
}
