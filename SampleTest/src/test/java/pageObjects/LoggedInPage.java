package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoggedInPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By pageTitle  = By.cssSelector("h1.post-title");
	private final By logoutLink = By.linkText("Log out");

	public LoggedInPage(WebDriver driver) {
		this.driver = driver;
		this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public String getPageTitle() {
		wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
		return driver.findElement(pageTitle).getText();
	}

	public boolean isLogoutVisible() {
		return !driver.findElements(logoutLink).isEmpty();
	}
}
