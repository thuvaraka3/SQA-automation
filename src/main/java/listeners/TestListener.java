
package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ExtentManager;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    private ExtentReports extent;
    private ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
        extentTest = extent.createTest(result.getMethod().getMethodName());
        logger.info("Starting test: {}", result.getMethod().getMethodName());
    }

    // Called when a @Test method passes (no exceptions thrown)
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.pass("Test passed");
        logger.info("Test passed: {}", result.getMethod().getMethodName());
    }

    // Called when a @Test method throws an exception (assertion failed, etc.)
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.fail(result.getThrowable()); // Log the exception details
        logger.error("Test failed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.skip("Test skipped");
        logger.warn("Test skipped: {}", result.getMethod().getMethodName());
    }

    // Called when the entire test suite finishes execution
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
        logger.info("Test suite finished: {}", context.getName());
    }
}
