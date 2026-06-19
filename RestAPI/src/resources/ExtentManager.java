package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = ConfigReader.get("extent.report.path");
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName(ConfigReader.get("extent.report.name"));
            reporter.config().setDocumentTitle(ConfigReader.get("extent.report.title"));
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester Name", ConfigReader.get("extent.tester.name"));
        }
        return extent;
    }
}
