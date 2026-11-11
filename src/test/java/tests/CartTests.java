

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ConfigReader;

public class CartTests extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;

    // Login before each test and set up the products page
    @BeforeMethod
    public void loginAndAddProducts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        productsPage = new ProductsPage(driver);
    }

    // TC-11: Verify that removing a product from cart works correctly
    @Test(priority = 11, groups = {"cart"})
    public void testRemoveProductFromCart() {
        logger.info("Starting test: Remove a product from the cart");

        // First add 2 products to the cart
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.addProductToCartByName("Sauce Labs Bike Light");
        String initialBadgeCount = productsPage.getCartBadgeCount();

        // Remove one of them from the products page
        productsPage.removeProductFromCartByName("Sauce Labs Backpack");

        // Navigate to the cart page to verify
        productsPage.clickCart();
        cartPage = new CartPage(driver);

        Assert.assertEquals(initialBadgeCount, "2", "Cart badge should initially show 2 items");
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item after removal");

        logger.info("Remove product from cart test completed");
    }

    // TC-12: Verify the cart badge updates correctly through add/remove sequence
    @Test(priority = 12, groups = {"cart"})
    public void testCartBadgeCountUpdate() {
        logger.info("Starting test: Verify cart badge count updates correctly");

        // Start with empty cart (badge should be 0 or invisible = "0")
        String initialCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(initialCount, "0", "Cart badge should initially be 0");

        // Add 1 item -> badge becomes 1
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Cart badge should show 1 after adding 1 product");

        // Add another item -> badge becomes 2
        productsPage.addProductToCartByName("Sauce Labs Onesie");
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2", "Cart badge should show 2 after adding 2 products");

        // Remove first item -> badge goes back to 1
        productsPage.removeProductFromCartByName("Sauce Labs Backpack");
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Cart badge should show 1 after removing 1 product");

        logger.info("Cart badge count update test completed");
    }
}
