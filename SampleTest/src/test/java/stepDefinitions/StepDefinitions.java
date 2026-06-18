package stepDefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private static final String LOGIN_URL = "https://practicetestautomation.com/practice-test-login/";

	private WebDriver driver;
	private WebDriverWait wait;

	@Before
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Given("the user is on the practice test login page")
	public void the_user_is_on_the_practice_test_login_page() {
		driver.get(LOGIN_URL);
	}

	@When("the user logs in with username {string} and password {string}")
	public void the_user_logs_in_with_username_and_password(String username, String password) {
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submit")).click();
	}

	@Then("the user should see the success message {string}")
	public void the_user_should_see_the_success_message(String expectedMessage) {
		wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
		String actualMessage = driver.findElement(By.cssSelector("h1.post-title")).getText();
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	@Then("the user should see the error message {string}")
	public void the_user_should_see_the_error_message(String expectedError) {
		String actualError = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("error")))).getText();
		Assert.assertEquals(expectedError, actualError);
	}
}
