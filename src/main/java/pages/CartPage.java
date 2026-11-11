// ============================================================
// CartPage.java - Page Object for the Shopping Cart page
// ============================================================
// This class represents the cart page where users can see what
// they've added, remove items, or proceed to checkout.
// ============================================================

package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    // ---- Locators ----

    // Page title (should be "Your Cart")
    private final By pageTitle = By.cssSelector("span[data-test='title']");

    // Each product row in the cart
    private final By cartItems = By.cssSelector(".cart_item");

    // Product name link inside each cart item
    private final By cartItemNames = By.cssSelector(".inventory_item_name");

    // The "Checkout" button to proceed with the order
    private final By checkoutButton = By.id("checkout");

    // The "Continue Shopping" button to go back to products page
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Returns the page title (e.g. "Your Cart")
    public String getTitle() {
        return getText(pageTitle);
    }

    // Returns how many items are currently in the cart
    public int getCartItemCount() {
        return getElements(cartItems).size();
    }

    // Gets a list of all product names currently in the cart
    public List<String> getCartItemNames() {
        List<WebElement> elements = getElements(cartItemNames);
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) {
            names.add(e.getText());
        }
        return names;
    }

    // Removes a product from the cart by its name
    // Uses XPath to find the product name <div>, then navigates UP to the cart_item
    // <div> ancestor and finds the <button> inside it
    public void removeItemByName(String productName) {
        By removeButton = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button");
        click(removeButton);
    }

    // Clicks the "Checkout" button to start the checkout process
    public void clickCheckout() {
        click(checkoutButton);
    }

    // Clicks "Continue Shopping" to return to the products page
    public void clickContinueShopping() {
        click(continueShoppingButton);
    }
}
