package com.herokuapp.theinternet;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

public class ExceptionsTests {
	
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
	
	
	@Test
	public void staleElementTest() {
		driver.get("http://the-internet.herokuapp.com/dynamic_controls");
		WebElement checkBox = driver.findElement(By.id("checkbox"));
		WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		
		removeButton.click();
		
//		wait.until(ExpectedConditions.invisibilityOf(checkBox));
//		Assert.assertFalse(checkBox.isDisplayed());
		
//		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkBox)), "Checkbox is still visible but shouldn't be");
		
		Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkBox)), "Checkbox is still visible but shouldn't be");
		
		WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
		addButton.click();
		
		checkBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
		
		Assert.assertTrue(checkBox.isDisplayed(), "Checkbox is still not visible but should be");
		
	}
	
	
	@Test
	public void challengeTest() {
		driver.get("http://the-internet.herokuapp.com/dynamic_controls");
		WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(),'Enable')]"));
//		WebElement textField = driver.findElement(By.xpath("//form[@id='input-example']/input[@type='text']"));
		WebElement textField = driver.findElement(By.xpath("(//input)[2]"));
		
		enableButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
		wait.until(ExpectedConditions.elementToBeClickable(textField));
		
		textField.sendKeys("Sample Text");
		
		Assert.assertEquals("Sample Text", textField.getAttribute("value"), "Input Text is different from expected");		
	}
	
	@Parameters({ "expectedMessage" })
	@Test(priority = 1, groups = { "exceptionsTests", "smokeTests" })
	private void notVisibleTest(String expectedMessage) {
		//Open test page
		String url = "https://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get(url);
		System.out.println("The page is opened");
		
		//Sleep for 3 secs
		sleep(1000);
		
		//Click start button
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
//		wait.until(ExpectedConditions.elementToBeClickable(startButton));
		startButton.click();

		//Validate message
		WebDriverWait waitForMsg = new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement successMsg = driver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));
		WebElement successMsg = driver.findElement(By.id("finish"));
		waitForMsg.until(ExpectedConditions.visibilityOf(successMsg));
		String actualMsg = successMsg.getText();
		Assert.assertTrue(actualMsg.contains(expectedMessage), "Actual message does not contain the expected message. \n actualMsg -> "+actualMsg+" \n expectedMsg -> "+expectedMessage);
		
	}
	
	
	@Parameters({ "expectedMessage" })
	@Test(priority = 2, groups = { "exceptionsTests", "smokeTests" })
	private void timeoutExceptionTest(String expectedMessage) {
		//Open test page
		String url = "https://the-internet.herokuapp.com/dynamic_loading/1";
		driver.get(url);
		System.out.println("The page is opened");
		
		//Sleep for 3 secs
		sleep(1000);
		
		//Click start button
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
		startButton.click();

		//Validate message
		WebDriverWait waitForMsg = new WebDriverWait(driver, Duration.ofSeconds(1));
		WebElement successMsg = driver.findElement(By.id("finish"));
		try {
			waitForMsg.until(ExpectedConditions.visibilityOf(successMsg));
		} catch (TimeoutException e) {
			System.out.println("Exception caught");
			sleep(4000);
		}
		
		String actualMsg = successMsg.getText();
		Assert.assertTrue(actualMsg.contains(expectedMessage), "Actual message does not contain the expected message. \n actualMsg -> "+actualMsg+" \n expectedMsg -> "+expectedMessage);
		
	}
	
	
	@Parameters({ "expectedMessage" })
	@Test(priority = 3, groups = { "exceptionsTests", "smokeTests" })
	private void noSuchElementTest(String expectedMessage) {
		//Open test page
		driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
		
		//Click start button
		WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
		startButton.click();
		
//		WebDriverWait waitForMsg = new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement successMsg = waitForMsg.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
//		
//		String actualMsg = successMsg.getText();
//		Assert.assertTrue(actualMsg.contains(expectedMessage), "Actual message does not contain the expected message. \n actualMsg -> "+actualMsg+" \n expectedMsg -> "+expectedMessage);
		
		
		WebDriverWait waitForMsg = new WebDriverWait(driver, Duration.ofSeconds(10));
		assertTrue(waitForMsg.until(ExpectedConditions.textToBePresentInElementLocated(By.id("finish"), expectedMessage)), "Actual message does not contain the expected message. \n expectedMsg -> "+expectedMessage);
		
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
