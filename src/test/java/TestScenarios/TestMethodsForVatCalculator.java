package TestScenarios;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Objects.CalculatorVat;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestMethodsForVatCalculator {
	WebDriver driver; //Driver object it will be sent as a param when CalculatorVat object will be initialized.
	CalculatorVat page; 
	
	//Before test code part will execute before any Test scenario. It will initialize Web Chrome driver and will load the web page.
	@BeforeTest
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.get("https://www.calculator.net/vat-calculator.html");
		page = new CalculatorVat(driver);
	}
	
	
	
	@Test(priority = 0)
	public void verifyTitle()
	{
		System.out.println("Executing Test Case 1..Page Title Verification");
		Assert.assertEquals(page.getTitle(), "VAT Calculator");
	}
	
	@Test (priority = 1)
	public void calculatorComponentDisplayed()
	{	
		Assert.assertEquals(page.calculatorComponentVisible(), true);
		
	}
	
	//Verify a case, When NET before VAT price is not there, It should automatically generate after calculation when VATE rate and Final VAT Inclusive price is there.
	
	@Test (priority = 2)
	public void validationCase1() {
		Assert.assertEquals(page.validationAtleastTwoValues(), "Please provide at least two values to calculate.");
	}
	@Test (priority = 3)
	public void validationCase2() {
		page.validationOfCorrectValues();
	}
	@Test (priority = 4)
	public void validationWithZeroInput() {
		page.validationWithZeroInput();
	}
	
	@Test (priority = 5)
	public void validateResultCase1() {
		page.validateResultWithZeroVATRate();
	}
	
	
	@Test (priority = 6)
	public void validateResultCase3() {
		page.validateResultWithoutBeforeVATPriceAndValidVATRate();
	}
	
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}
