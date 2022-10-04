package com.herokuapp.theinternet;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
	
	private WebDriver driver;
	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun=true)
	private void setUp(@Optional("chrome") String browser) {
		//Create driver
		
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Starting with default as browser is invalid -> "+browser);
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		
		//Sleep for 3 secs
		sleep(1000);
		
		//Maximize browser window
		driver.manage().window().maximize();
		
		//Implecit wait
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTests() {
		System.out.println("Starting logintest");
		
		//Open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("The page is opened");
		
		//Sleep for 3 secs
		sleep(1000);
		
		//Enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");
		sleep(1000);
		
		//Enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//Click login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
		sleep(2000);
		
		//Verifications
		
		//new url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");
		
		//Logout button is visible
//		WebElement logoutButton = driver.findElement(By.xpath("//a[@class='button secondary radius broken']"));
		WebElement logoutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not visible");
		
		//Successful login message
		//WebElement successMsg = driver.findElement(By.cssSelector("div#flash"));
//		WebElement successMsg = driver.findElement(By.className("success"));
		WebElement successMsg = driver.findElement(By.xpath("/html//div[@id='flash']"));
		String expectedMsg = "You logged into a secure area!";
		String actualMsg = successMsg.getText();
//		Assert.assertEquals(actialMsg, expectedMsg, "Actual message is not the same as expected");
		Assert.assertTrue(actualMsg.contains(expectedMsg), "Actual message does not contain the expected message. \n actualMsg -> "+actualMsg+" \n expectedMsg -> "+expectedMsg);
		
	}

	
	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTests(String usernameParam, String passwordParam, String expectedMessageParam) {
		System.out.println("Starting Incorrect Username test with "+usernameParam+ " and password "+passwordParam);
		
		//Open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened");
		
		//enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys(usernameParam);
		sleep(1000);
		
		//Enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(passwordParam);
		sleep(1000);
		
		//Click login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();
		sleep(2000);
		
		WebElement errorMsg = driver.findElement(By.xpath("/html//div[@id='flash']"));
		String actualMsg = errorMsg.getText();
		Assert.assertTrue(actualMsg.contains(expectedMessageParam), "Actual message does not contain the expected message. \n actualMsg -> "+actualMsg+" \n expectedMsg -> "+expectedMessageParam);
		
	}

	private void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterMethod(alwaysRun=true)
	private void tearDown() {
		//Close browser
		driver.quit();
	}

}
