
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // ---- Locators ----
    // Each locator is a way to find a specific HTML element on the page
    // 'private final' means: only this class can use it, and it never changes

    // The username text field - found by its HTML id "user-name"
    private final By usernameField = By.id("user-name");

    // The password text field
    private final By passwordField = By.id("password");

    // The "Login" submit button
    private final By loginButton = By.id("login-button");

    // The error message box (appears when login fails - like wrong password)
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    // The small X button that closes/dismisses the error message
    private final By errorButton = By.cssSelector(".error-button");

    // Constructor: calls parent BasePage constructor to set up driver, wait, and actions
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Types the given text into the username field
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    // Types the given text into the password field
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    // Clicks the Login button to submit the login form
    public void clickLogin() {
        click(loginButton);
    }

    // A helper method that does all 3 steps at once (combines above methods)
    // This is called a "convenience method" - makes test code shorter and cleaner
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Returns the text of the error message (so tests can check what it says)
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    // Returns true/false - is the error message visible on screen right now?
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    // Clicks the X button on the error message to close it
    public void dismissError() {
        click(errorButton);
    }

    // Checks if the login button is displayed (useful to verify we're on the login page)
    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }
}
