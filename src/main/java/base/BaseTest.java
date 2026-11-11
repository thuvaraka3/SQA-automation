
package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utilities.ConfigReader;
import utilities.DriverFactory;
import listeners.TestListener;

// Linking the TestListener so ExtentReports works automatically for all tests
@Listeners(TestListener.class)
public class BaseTest {

    // 'protected' means child classes (like LoginTests) can access this directly
    protected WebDriver driver;

    // Logger for writing logs - static so all test classes share the same logger instance
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    // Runs BEFORE each @Test method - sets up the browser and navigates to the website
    @BeforeMethod
    public void setUp() {
        // Read which browser to use from config.properties (e.g. "chrome")
        String browser = ConfigReader.getBrowser();
        logger.info("Launching browser: {}", browser);

        // Create a new WebDriver instance (opens a browser window)
        driver = DriverFactory.createDriver(browser);

        // Navigate to the Sauce Demo URL from config.properties
        driver.get(ConfigReader.getBaseUrl());
        logger.info("Navigated to URL: {}", ConfigReader.getBaseUrl());
    }

    // Runs AFTER each @Test method - closes the browser and cleans up
    @AfterMethod
    public void tearDown() {
        logger.info("Closing browser");
        DriverFactory.quitDriver(); // quits the browser and resets driver to null
    }
}
