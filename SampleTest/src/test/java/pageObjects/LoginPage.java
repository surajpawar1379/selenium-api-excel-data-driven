package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By usernameField = By.id("username");
	private final By passwordField = By.id("password");
	private final By submitButton  = By.id("submit");
	private final By errorMessage  = By.id("error");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void navigateTo(String url) {
		driver.get(url);
	}

	public void enterUsername(String username) {
		driver.findElement(usernameField).sendKeys(username);
	}

	public void enterPassword(String password) {
		driver.findElement(passwordField).sendKeys(password);
	}

	public void clickSubmit() {
		driver.findElement(submitButton).click();
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickSubmit();
	}

	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
	}
}
