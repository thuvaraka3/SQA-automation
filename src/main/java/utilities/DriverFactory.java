// ============================================================
// DriverFactory.java - Creates and manages the WebDriver instance
// ============================================================
// This class handles browser creation using the Singleton pattern.
// Instead of creating a new driver in every test, we call
// DriverFactory.createDriver("chrome") which gives us a ready-to-use
// browser instance.
// ============================================================

package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    // Static variable holds the single driver instance (Singleton pattern)
    // 'private' so no other class can access it directly - must use getDriver()
    private static WebDriver driver;

    // Creates and returns a WebDriver based on the browser name passed in
    // browser parameter: "chrome" or "firefox" (default: chrome)
    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) { // Convert to lowercase so "Chrome" and "chrome" both work

            case "firefox":
                // Tell Selenium where the GeckoDriver (Firefox driver) executable is
                System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized"); // Open browser maximized
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "chrome":
            default:
                // WebDriverManager detects the installed Chrome version and
                // automatically downloads a matching ChromeDriver for us
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized"); // I want the window maximized
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        return driver; // Return the newly created browser instance
    }

    // Returns the current driver instance so other classes can use it
    public static WebDriver getDriver() {
        return driver;
    }

    // Quits the browser and sets driver back to null
    // Always call this after tests finish to free up memory/processes
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();  // Close the browser window
            driver = null;  // Reset so next test creates a fresh browser
        }
    }
}
