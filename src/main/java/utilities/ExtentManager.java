package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            File reportDir = new File("reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/extent-report.html");
            sparkReporter.config().setDocumentTitle("Sauce Demo Automation Report");
            sparkReporter.config().setReportName("Sauce Demo Test Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
