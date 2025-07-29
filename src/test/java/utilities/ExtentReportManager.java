package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class ExtentReportManager implements ITestListener {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        // Timestamp for report file name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Specify location of the report
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        // Report configuration
        sparkReporter.config().setDocumentTitle("API Automation Report"); // Title of the report
        sparkReporter.config().setReportName("User API Test Results");    // Name of the report
        sparkReporter.config().setTheme(Theme.DARK);                      // Theme

        // Attach reporter to ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set environment info
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Your Name");
    }

    // Optionally add onFinish() to flush the report
    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
