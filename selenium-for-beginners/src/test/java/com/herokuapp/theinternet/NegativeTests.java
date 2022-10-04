package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
	
	/*
	 * @Parameters({ "username", "password", "expectedMessage" })
	 * 
	 * @Test(priority = 1, groups = { "negativeTests", "smokeTests" }) public void
	 * negativeLoginTest(String usernameParam, String passwordParam, String
	 * expectedMessageParam) {
	 * System.out.println("Starting Incorrect Username test with "+usernameParam+
	 * " and password "+passwordParam);
	 * 
	 * //Create driver System.setProperty("webdriver.chrome.driver",
	 * "src/main/resources/chromedriver.exe"); WebDriver driver = new
	 * ChromeDriver();
	 * 
	 * sleep(1000);
	 * 
	 * driver.manage().window().maximize();
	 * 
	 * //Open test page String url = "http://the-internet.herokuapp.com/login";
	 * driver.get(url); System.out.println("Page is opened");
	 * 
	 * //enter username WebElement username = driver.findElement(By.id("username"));
	 * username.sendKeys(usernameParam); sleep(1000);
	 * 
	 * //Enter password WebElement password =
	 * driver.findElement(By.name("password")); password.sendKeys(passwordParam);
	 * sleep(1000);
	 * 
	 * //Click login button WebElement loginButton =
	 * driver.findElement(By.tagName("button")); loginButton.click(); sleep(2000);
	 * 
	 * WebElement errorMsg =
	 * driver.findElement(By.xpath("/html//div[@id='flash']")); String actualMsg =
	 * errorMsg.getText();
	 * Assert.assertTrue(actualMsg.contains(expectedMessageParam),
	 * "Actual message does not contain the expected message. \n actualMsg -> "
	 * +actualMsg+" \n expectedMsg -> "+expectedMessageParam);
	 * 
	 * //Close browser driver.quit();
	 * 
	 * }
	 */
	
	
	/*
	 * @Test(priority = 2, enabled = true, groups = { "negativeTests" }) public void
	 * incorrectPasswordTest() {
	 * System.out.println("Starting Incorrect Password test");
	 * 
	 * //Create driver System.setProperty("webdriver.chrome.driver",
	 * "src/main/resources/chromedriver.exe"); WebDriver driver = new
	 * ChromeDriver();
	 * 
	 * sleep(1000);
	 * 
	 * driver.manage().window().maximize();
	 * 
	 * //Open test page String url = "http://the-internet.herokuapp.com/login";
	 * driver.get(url); System.out.println("Page is opened");
	 * 
	 * //enter username WebElement username = driver.findElement(By.id("username"));
	 * username.sendKeys("tomsmith"); sleep(1000);
	 * 
	 * //Enter password WebElement password =
	 * driver.findElement(By.name("password")); password.sendKeys("incorrect!");
	 * sleep(1000);
	 * 
	 * //Click login button WebElement loginButton =
	 * driver.findElement(By.tagName("button")); loginButton.click(); sleep(2000);
	 * 
	 * WebElement errorMsg =
	 * driver.findElement(By.xpath("/html//div[@id='flash']")); String expectedMsg =
	 * "Your password is invalid!"; String actualMsg = errorMsg.getText();
	 * Assert.assertTrue(actualMsg.contains(expectedMsg),
	 * "Actual message does not contain the expected message. \n actualMsg -> "
	 * +actualMsg+" \n expectedMsg -> "+expectedMsg);
	 * 
	 * //Close browser driver.quit();
	 * 
	 * }
	 */
	
	private void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
