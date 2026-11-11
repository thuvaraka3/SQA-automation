// CheckoutTests.java - Test class for Checkout flow

// This class tests the complete checkout process - from cart
// to order completion. It covers the happy path and error scenarios.

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;

public class CheckoutTests extends BaseTest {

    // Logs in and adds a single product to the cart before each test
    @BeforeMethod
    public void loginAndAddProduct() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        ProductsPage productsPage = new ProductsPage(driver);
        // Add Sauce Labs Backpack so we have something in the cart for checkout
        productsPage.addProductToCartByName("Sauce Labs Backpack");
    }

    // TC-13: The full happy-path checkout flow - login -> add to cart -> checkout -> finish
    @Test(priority = 13, groups = {"smoke", "checkout"})
    public void testCompleteCheckoutSuccessfully() {
        logger.info("Starting test: Complete checkout successfully with valid information");

        // Step 1: Go to cart and click Checkout
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Step 2: Fill in checkout information and continue
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation("Thuva", "Pallavi", "12345");

        // Step 3: Verify order overview page has correct details
        OverviewPage overviewPage = new OverviewPage(driver);
        Assert.assertEquals(overviewPage.getTitle(), "Checkout: Overview", "Should be on overview page");
        Assert.assertEquals(overviewPage.getItemCount(), 1, "Overview should show 1 item");
        Assert.assertTrue(overviewPage.getItemTotal().contains("$"), "Item total should contain $");

        // Step 4: Confirm the purchase
        overviewPage.clickFinish();

        // Step 5: Verify order completion page
        CompletePage completePage = new CompletePage(driver);
        Assert.assertEquals(completePage.getTitle(), "Checkout: Complete!", "Should be on complete page");
        Assert.assertTrue(completePage.isCompleteHeaderDisplayed(), "Complete header should be displayed");
        Assert.assertEquals(completePage.getCompleteHeader(), "Thank you for your order!",
                "Should show thank you message");

        logger.info("Complete checkout test completed successfully");
    }

    // TC-14: Verify validation error when required fields are left empty
    @Test(priority = 14, groups = {"checkout"})
    public void testCheckoutWithMissingRequiredFields() {
        logger.info("Starting test: Checkout with missing required fields");

        // Navigate to checkout page
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Click Continue WITHOUT filling in any fields
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickContinue();

        // The website should show an error saying First Name is required
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(checkoutPage.getErrorMessage(),
                "Error: First Name is required",
                "Error message should indicate first name is required");

        logger.info("Checkout with missing fields test completed");
    }

    // TC-15: Verify the complete order confirmation including dispatch message and navigation
    @Test(priority = 15, groups = {"smoke", "checkout"})
    public void testOrderConfirmationAfterSuccessfulPurchase() {
        logger.info("Starting test: Verify order confirmation after successful purchase");

        // Navigate through checkout flow
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation("Thuva", "Pallavi", "67890");

        OverviewPage overviewPage = new OverviewPage(driver);
        overviewPage.clickFinish();

        // Verify the completion page details
        CompletePage completePage = new CompletePage(driver);

        Assert.assertEquals(completePage.getTitle(), "Checkout: Complete!", "Should be on complete page");
        Assert.assertTrue(completePage.isCompleteHeaderDisplayed(), "Complete header should be displayed");
        Assert.assertEquals(completePage.getCompleteHeader(), "Thank you for your order!",
                "Should display 'Thank you for your order!'");
        Assert.assertTrue(completePage.getCompleteText().contains("dispatched"),
                "Complete text should mention order dispatch");

        // Click "Back Home" and verify we return to the products page
        completePage.clickBackHome();
        Assert.assertTrue(productsPage.isProductPageDisplayed(), "Should return to products page");

        logger.info("Order confirmation test completed successfully");
    }
}
