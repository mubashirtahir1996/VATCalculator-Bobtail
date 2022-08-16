package Objects;

import java.util.Arrays;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

// Author Mubashir Hassan Tahir
// This is my Object Class for VAT Calculator page, This class contains all the web element Locators It is useful in reducing code duplication,
// and improves test case maintenance. 

public class CalculatorVat {

	
	WebDriver driver;  		// Declaring the driver object, It will be initialized in the Constructor ,its object will initialized in TestScenario Class methods.
	
	// Following are Web element locators, Storing them in a variable as they are reused in different methods. 
	By caulculatorBoxComponent = By.xpath("//td //table");  // Calculator Table Component 
	By beforeTax = By.xpath("//input[@name='beforetax']"); // Before Tax Input Field 
	By vatRate = By.xpath("//input[@class='inlong inpct']"); // Value added tax Rate Input Field.
	By finalPrice = By.xpath("//input[@name='finalprice']"); // Final price input field 
	By calculateButton = By.xpath("//input[@value='Calculate']"); // Calculate Button 
	By errorMessage = By.xpath("//font[@color='red']"); // Error message element
	By resultHeading = By.xpath("//h2[@class='h2result']"); //Result label heading
	By resultValue = By.xpath("//div[@class='verybigtext']"); // Result values with message.

	// Constructor to initialize Web driver.
	public CalculatorVat(WebDriver driver)
	{
		this.driver = driver;
	}
	
	// This method will return page tab title.
	public String getTitle() {
		String title = driver.getTitle();
		return title;
	}
	
	// This method will check if the main Table component is visible or not.
	public boolean calculatorComponentVisible() {
		return driver.findElement(caulculatorBoxComponent).isDisplayed();
	}
	
	// This method will check when all the input fields are empty and user clicks on Calculate button, Then a validation error message should appear.
	public String validationAtleastTwoValues() {
		
		driver.findElement(vatRate).clear();
		driver.findElement(finalPrice).clear();
		driver.findElement(calculateButton).click();
		return driver.findElement(errorMessage).getText().toString();
	}
	
	// This method will check for valid input i.e. If values are other than numerics, Validation Error message will be returned.
	public void validationOfCorrectValues() {
		
			driver.findElement(beforeTax).sendKeys("a");
			driver.findElement(vatRate).sendKeys("a");
			driver.findElement(finalPrice).sendKeys("a");
			driver.findElement(calculateButton).click();
			List<String> errorMessages = Arrays.asList("Please provide a valid net before VAT price.","Please provide a valid VAT tax rate.",
					"Please provide a valid final VAT inclusive price.");
			for(int i=0;i< driver.findElements(errorMessage).size() ;i++) {
				Assert.assertEquals(driver.findElements(errorMessage).get(i).getText(), errorMessages.get(i));
			}
			 
		}
	
	// This method will check if all values are entered "0", Validation error message for VAT price should be returned as 0 is acceptable for other 2 fields.
	public void validationWithZeroInput() {
		
		driver.findElement(beforeTax).clear();
		driver.findElement(vatRate).clear();
		driver.findElement(finalPrice).clear();
		
		
		driver.findElement(beforeTax).sendKeys("0");
		driver.findElement(vatRate).sendKeys("0");
		driver.findElement(finalPrice).sendKeys("0");
		driver.findElement(calculateButton).click();
		Assert.assertEquals(driver.findElement(errorMessage).getText().toString() ,"Please provide a valid net before VAT price.");
	}
	
	// This method will verify Positive test case with positive values, When VAT rate is 0%, original value will be the Final price as well.
	public void validateResultWithZeroVATRate() {
		
		driver.findElement(beforeTax).clear();
		driver.findElement(vatRate).clear();
		driver.findElement(finalPrice).clear();
		
		driver.findElement(beforeTax).sendKeys("100");
		driver.findElement(vatRate).sendKeys("0");
		driver.findElement(finalPrice).sendKeys("100");
		driver.findElement(calculateButton).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(driver.findElement(resultHeading).getText(), "Result");
		
		List<String> resultTexts = Arrays.asList("Net Before VAT Price: 100.00","VAT: 0.00% or 0.00","Final VAT Inclusive Price: 100.00");
		
		for(int i=0;i< driver.findElements(resultValue).size() ;i++) {
			Assert.assertEquals(driver.findElements(resultValue).get(i).getText(), resultTexts.get(i));
		}
	}
	
	//This method will verify if Before value is not given and VAT rate and Final value are given, Then Result should be return with the correct Before value.
	public void validateResultWithoutBeforeVATPriceAndValidVATRate() {
		driver.findElement(beforeTax).clear();
		driver.findElement(vatRate).clear();
		driver.findElement(finalPrice).clear();
		
		driver.findElement(vatRate).sendKeys("20");
		driver.findElement(finalPrice).sendKeys("120");
		driver.findElement(calculateButton).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(driver.findElement(resultHeading).getText(), "Result");
		
		List<String> resultTexts = Arrays.asList("Net Before VAT Price: 100.00","VAT: 20.00% or 20.00","Final VAT Inclusive Price: 120.00");
		
		for(int i=0;i< driver.findElements(resultValue).size() ;i++) {
			Assert.assertEquals(driver.findElements(resultValue).get(i).getText(), resultTexts.get(i));
		}
	}
}
