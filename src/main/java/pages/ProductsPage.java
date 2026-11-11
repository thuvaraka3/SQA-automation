
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends BasePage {

    // ---- Locators ----
    // The page heading/title (e.g. "Products")
    private final By pageTitle = By.cssSelector("span[data-test='title']");

    // All product cards on the page (each .inventory_item is one product)
    private final By inventoryItems = By.cssSelector(".inventory_item");

    // Product name links inside each product card
    private final By inventoryItemNames = By.cssSelector(".inventory_item_name");

    // Product price labels inside each product card
    private final By inventoryItemPrices = By.cssSelector(".inventory_item_price");

    // The sort dropdown (can choose A-Z, Z-A, price low-high, price high-low)
    private final By sortDropdown = By.cssSelector("select.product_sort_container");

    // The red badge on the shopping cart icon showing number of items
    private final By shoppingCartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");

    // The shopping cart icon/link in the top right corner
    private final By cartLink = By.cssSelector("a[data-test='shopping-cart-link']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // Helper method: converts "Sauce Labs Backpack" to "sauce-labs-backpack"
    // This matches the data-test format used on Sauce Demo's buttons
    private String toDataTestId(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }

    // Returns the page title text (e.g. "Products")
    public String getTitle() {
        return getText(pageTitle);
    }

    // Returns the number of products displayed on the page
    public int getProductCount() {
        return getElements(inventoryItems).size();
    }

    // Gets a list of all product names as Strings
    public List<String> getProductNames() {
        List<WebElement> elements = getElements(inventoryItemNames);
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) {
            names.add(e.getText()); // Extract visible text from each element
        }
        return names;
    }

    // Gets a list of all product prices as Double numbers (for sorting comparisons)
    public List<Double> getProductPrices() {
        List<WebElement> elements = getElements(inventoryItemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : elements) {
            // Price format is "$29.99" - remove $ and convert to double
            prices.add(Double.parseDouble(e.getText().replace("$", "")));
        }
        return prices;
    }

    // Selects a sort option from the dropdown by its value attribute
    // Options: "az" (A-Z), "za" (Z-A), "lohi" (Low-High price), "hilo" (High-Low price)
    public void selectSortOption(String option) {
        Select select = new Select(waitForElement(sortDropdown));
        select.selectByValue(option);
    }

    // Clicks "Add to cart" button for a specific product by its name
    // The button's data-test attribute follows a predictable pattern
    public void addProductToCartByName(String productName) {
        // e.g. button[data-test='add-to-cart-sauce-labs-backpack']
        By addButton = By.cssSelector("button[data-test='add-to-cart-" + toDataTestId(productName) + "']");
        By removeButton = By.cssSelector("button[data-test='remove-" + toDataTestId(productName) + "']");
        click(addButton);
        // Wait for the button text to change from "Add to cart" to "Remove"
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeButton));
    }

    // Clicks "Remove" button for a specific product by its name
    public void removeProductFromCartByName(String productName) {
        By removeButton = By.cssSelector("button[data-test='remove-" + toDataTestId(productName) + "']");
        By addButton = By.cssSelector("button[data-test='add-to-cart-" + toDataTestId(productName) + "']");
        click(removeButton);
        // Wait for the button to change back to "Add to cart"
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
    }

    // Returns the cart badge count as a string (e.g. "0", "1", "3")
    // Returns "0" if the badge isn't visible (meaning cart is empty)
    public String getCartBadgeCount() {
        if (isDisplayed(shoppingCartBadge)) {
            return getText(shoppingCartBadge);
        }
        return "0"; // No badge visible = cart is empty
    }

    // Clicks the shopping cart icon to navigate to the cart page
    public void clickCart() {
        click(cartLink);
    }

    // Checks if we're on the products page (title element is visible)
    public boolean isProductPageDisplayed() {
        return isDisplayed(pageTitle);
    }
}
