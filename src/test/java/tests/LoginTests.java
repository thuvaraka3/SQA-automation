
// LoginTests.java - Test class for Login page functionality

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ConfigReader;

public class LoginTests extends BaseTest {

    // TC-01: Verify that a user can login with valid username and password
    // This is the "happy path" - everything should work
    @Test(priority = 1, groups = {"smoke", "login"})
    public void testValidLogin() {
        logger.info("Starting test: Valid Login");

        // Create the LoginPage object and perform login with credentials from config
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        // After clicking login, we should land on the Products page
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductPageDisplayed(), "Products page should be displayed after login");
        Assert.assertEquals(productsPage.getTitle(), "Products", "Page title should be 'Products'");

        logger.info("Valid login test completed successfully");
    }

    // TC-02: Verify that an error message appears when the password is wrong
    @Test(priority = 2, groups = {"login"})
    public void testLoginWithInvalidPassword() {
        logger.info("Starting test: Login with invalid password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), "wrong_password");

        // The website should show an error message saying credentials don't match
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Error message should indicate invalid credentials");

        logger.info("Invalid password test completed");
    }

    // TC-03: Verify that an error message appears when the username is wrong
    @Test(priority = 3, groups = {"login"})
    public void testLoginWithInvalidUsername() {
        logger.info("Starting test: Login with invalid username");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", ConfigReader.getPassword());

        // Same error message as wrong password - website doesn't tell you which field is wrong
        // (this is a security feature - don't reveal which credential is incorrect)
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Error message should indicate invalid credentials");

        logger.info("Invalid username test completed");
    }

    // TC-04: Verify that an error appears when username field is left empty
    @Test(priority = 4, groups = {"login"})
    public void testLoginWithEmptyUsername() {
        logger.info("Starting test: Login with empty username");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", ConfigReader.getPassword());  // Empty username

        // The website has client-side validation for required fields
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username is required",
                "Error message should indicate username is required");

        logger.info("Empty username test completed");
    }

    // TC-05: Verify that an error appears when password field is left empty
    @Test(priority = 5, groups = {"login"})
    public void testLoginWithEmptyPassword() {
        logger.info("Starting test: Login with empty password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), "");  // Empty password

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Password is required",
                "Error message should indicate password is required");

        logger.info("Empty password test completed");
    }
}
