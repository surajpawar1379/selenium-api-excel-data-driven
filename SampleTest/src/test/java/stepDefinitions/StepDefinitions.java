package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoggedInPage;
import pageObjects.LoginPage;

public class StepDefinitions {

	private static final String LOGIN_URL = "https://practicetestautomation.com/practice-test-login/";

	private WebDriver driver;
	private LoginPage loginPage;
	private LoggedInPage loggedInPage;

	@Before
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
			options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080",
					"--no-sandbox", "--disable-dev-shm-usage");
		}
		driver = new ChromeDriver(options);
		loginPage    = new LoginPage(driver);
		loggedInPage = new LoggedInPage(driver);
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Given("the user is on the practice test login page")
	public void the_user_is_on_the_practice_test_login_page() {
		loginPage.navigateTo(LOGIN_URL);
	}

	@When("the user logs in with username {string} and password {string}")
	public void the_user_logs_in_with_username_and_password(String username, String password) {
		loginPage.login(username, password);
	}

	@Then("the user should see the success message {string}")
	public void the_user_should_see_the_success_message(String expectedMessage) {
		Assert.assertEquals(expectedMessage, loggedInPage.getPageTitle());
	}

	@Then("the user should see the error message {string}")
	public void the_user_should_see_the_error_message(String expectedError) {
		Assert.assertEquals(expectedError, loginPage.getErrorMessage());
	}
}
