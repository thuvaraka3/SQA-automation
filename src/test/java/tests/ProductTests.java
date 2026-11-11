
// ProductTests.java - Test class for Products page functionality

// This class tests the main products page including sorting and
// adding items to the shopping cart.

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ConfigReader;

import java.util.Collections;
import java.util.List;

public class ProductTests extends BaseTest {

    // Class-level variable so all test methods can use it
    private ProductsPage productsPage;

    // This runs before each @Test method in this class
    // Logs in with valid credentials and goes to the products page
    @BeforeMethod
    public void loginAndNavigateToProducts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        productsPage = new ProductsPage(driver);
    }

    // TC-06: Verify that the products page loads correctly with products visible
    @Test(priority = 6, groups = {"smoke", "products"})
    public void testProductsPageLoads() {
        logger.info("Starting test: Verify products page loads successfully");

        Assert.assertTrue(productsPage.isProductPageDisplayed(), "Products page should be displayed");
        Assert.assertEquals(productsPage.getTitle(), "Products", "Page title should be 'Products'");
        Assert.assertTrue(productsPage.getProductCount() > 0, "Product count should be greater than 0");

        logger.info("Products page loads test completed");
    }

    // TC-07: Verify that sorting by Name (A to Z) works correctly
    @Test(priority = 7, groups = {"products"})
    public void testProductSortingByNameAZ() {
        logger.info("Starting test: Verify product sorting by name A-Z");

        // Select "Name (A to Z)" from the sort dropdown
        productsPage.selectSortOption("az");

        // Get the current order of product names from the page
        List<String> actualNames = productsPage.getProductNames();

        // Create a sorted copy of the list to compare against
        List<String> sortedNames = new java.util.ArrayList<>(actualNames);
        Collections.sort(sortedNames);

        // If the page's order matches the sorted order, the feature works!
        Assert.assertEquals(actualNames, sortedNames, "Products should be sorted A-Z");

        logger.info("Product sorting A-Z test completed");
    }

    // TC-08: Verify that sorting by price (Low to High) works correctly
    @Test(priority = 8, groups = {"products"})
    public void testProductSortingByPriceLowToHigh() {
        logger.info("Starting test: Verify product sorting by price low to high");

        // Select "Price (low to high)" from the sort dropdown
        productsPage.selectSortOption("lohi");

        // Get current prices
        List<Double> actualPrices = productsPage.getProductPrices();

        // Create a sorted copy
        List<Double> sortedPrices = new java.util.ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(actualPrices, sortedPrices, "Products should be sorted by price low to high");

        logger.info("Product sorting by price test completed");
    }

    // TC-09: Verify adding a single product shows badge count of 1
    @Test(priority = 9, groups = {"products"})
    public void testAddOneProductToCart() {
        logger.info("Starting test: Add one product to cart");

        String productName = "Sauce Labs Backpack";
        productsPage.addProductToCartByName(productName);

        // The cart badge in the top right should now show "1"
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Cart badge should show 1 item");

        logger.info("Add one product to cart test completed");
    }

    // TC-10: Verify adding multiple products increments badge correctly
    @Test(priority = 10, groups = {"products"})
    public void testAddMultipleProductsToCart() {
        logger.info("Starting test: Add multiple products to cart");

        // Add 3 different products
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.addProductToCartByName("Sauce Labs Bike Light");
        productsPage.addProductToCartByName("Sauce Labs Bolt T-Shirt");

        // Badge should now show total count of 3
        Assert.assertEquals(productsPage.getCartBadgeCount(), "3", "Cart badge should show 3 items");

        logger.info("Add multiple products to cart test completed");
    }
}
