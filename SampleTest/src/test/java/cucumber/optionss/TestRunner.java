package cucumber.optionss;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/features",
		glue = {"stepDefinitions"},
		plugin = {
				"pretty",
				"json:target/jsonReports/cucumber-report.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		}
)
public class TestRunner {


}
