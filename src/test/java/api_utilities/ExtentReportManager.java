package api_utilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener, IExecutionListener {

    private ExtentReports extent;
    private ExtentSparkReporter sparkReporter;
    private ExtentTest test;
    private String reportName;

    // Constructor to initialize the report
    public ExtentReportManager() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportName = "Test-Report-" + timestamp + ".html";

        // Define the file path where the report will be saved
        String reportPath = ".\\reports\\" + reportName;

        // Create the Spark Reporter and attach it to the ExtentReports object
        sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Set the dark theme for the report
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Adding system information
        extent.setSystemInfo("Application", "YourAppName");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
    }

    // Method to run before the start of test execution
    public void onStart() {
        System.out.println("Test Execution Started");
    }

    // Method to run when a test is successful
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory("Passed Tests"); // Assign a category (e.g., Passed Tests)
        test.pass("Test Passed");
        test.pass("Additional log: Test passed successfully");

        // // Example of creating a child node for a specific test step
        // ExtentTest node = test.createNode("Step 1: Validate Login");
        // node.pass("Login validated successfully");

        // Test log
        test.info("Test finished successfully at: " + new Date());
    }

    // Method to run when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory("Failed Tests"); // Assign a category (e.g., Failed Tests)
        test.fail("Test Failed: " + result.getThrowable());

        // // Example of creating a child node for the failed step
        // ExtentTest node = test.createNode("Step 1: Validate Login");
        // node.fail("Login validation failed");

        // Test log
        test.info("Failure reason: " + result.getThrowable().getMessage());
    }

    // Method to run when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory("Skipped Tests"); // Assign a category (e.g., Skipped Tests)
        test.skip("Test Skipped: " + result.getThrowable());

        // Example of creating a child node for the skipped step
        // ExtentTest node = test.createNode("Step 1: Validate Login");
        // node.skip("Login validation skipped");

        // Test log
        test.info("Test was skipped due to: " + result.getThrowable().getMessage());
    }

    // Method to run before a test method is invoked
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory("General Tests"); // Assign a generic category
    }

    
    // Method to run after all tests are finished
    public void onFinish() {
        extent.flush();
        System.out.println("Test Execution Finished. Report generated at: " + reportName);
    }
}
