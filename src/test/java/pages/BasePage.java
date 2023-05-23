package pages;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;

import util.LoggerUtil;
import util.ReportUtil;
import util.TestProperties;

public class BasePage {
	protected WebDriver driver;

	protected FluentWait<WebDriver> waiter, invisibleWaiter;
	protected SoftAssert softAssert;

	/**
	 * BasePage constructor
	 * 
	 * @param driver
	 */
	public BasePage(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
		int waitTimeout = Integer.parseInt(TestProperties.getProperty("waitTimeout"));
		waiter = new FluentWait<>(driver).ignoring(NoSuchElementException.class, WebDriverException.class)
				.withTimeout(Duration.ofSeconds(waitTimeout)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class).ignoring(TimeoutException.class);


		softAssert = new SoftAssert();
	}

	/**
	 * Use the Actions class to click an element
	 * 
	 * @param element the element to click
	 */
	public void ActionsClickElement(WebElement element) {
		LoggerUtil.log("Actions Click element " + element.toString());

		WaitUntilClickable(element);

		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}

	/**
	 * Clear a text box using backspace
	 * 
	 * @param element The element to clear
	 */
	public void ClearTextboxWithBackspace(WebElement element) {
		WaitUntilClickable(element);

		String fieldValue = element.getAttribute("value");

		try {
			for (int i = 0; i < fieldValue.length(); i++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		} catch (Exception e) {
			doSoftAssertFailure("Failed adding characters to field");
		}

	}



	/**
	 * Click an element
	 * 
	 * @param element The element to click
	 */
	public void ClickElement(WebElement element) {
		LoggerUtil.log("Click element " + element.toString());

		SteadyWait(element);

//		WaitUntilEnabled(element).click();
		retryingFindClick(element);
	}

	
	/**
	 * Wait for document readystate to be complete
	 */
	public void DomWait() {
		LoggerUtil.log("Waiting for dom load to complete");

		try {
			this.waiter.until(
					d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			doSoftAssertFailure("Failed waiting for DOM load to complete");
		}

	}

	/**
	 * Do a soft assertion. Note a difference but don't kill the test
	 * 
	 * @param msg
	 */
	public void doSoftAssertFailure(String msg) {
		System.out.println(msg);
		softAssert.assertTrue(false, msg);
		// ReportUtil.addScreenShot(LogStatus.FAIL, msg);
	}



	/**
	 * Check if an element exists
	 * 
	 * @param element the element to look for
	 * @return true if found/false if not
	 */
	public boolean Exists(WebElement element) {
		try {
			WaitUntilVisible(element);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get Element Text
	 * 
	 * @param element The element to get text from
	 * @return The String text found in the element
	 */
	public String GetElementText(WebElement element) {

		try {
			String elementText = WaitUntilVisible(element).getText();

			ReportUtil.logMessage("Get Element Text", elementText);

			return elementText;
		} catch (Exception e) {
			doSoftAssertFailure("Failed to get text from element");
			return "";
		}

	}



	/**
	 * Use JavaScript executor to click an element
	 * 
	 * @param element the element to click
	 */
	public void JavaScriptClickElement(WebElement element) {
		LoggerUtil.log("JavaScript Executor Click element " + element.toString());

		WaitUntilClickable(element);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Use JavaScript executor to set a text box value
	 * 
	 * @param element the element to set
	 * @param value   the value to use
	 */
	public void JavaScriptSetTextBox(WebElement element, String value) {
		LoggerUtil.log("JavaScript Fill text box " + element.toString() + " : " + value);

		WaitUntilVisible(element);

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + value + "';", element);
		} catch (Exception e) {
			doSoftAssertFailure(String.format("Could not set element text to '%s'", value));
		}

	}

    public void GetAssertFailures(){
	softAssert.assertAll();
 }



	/**
	 * Move focus to an element
	 * 
	 * @param element The element to move to
	 * @return An instance of the element
	 */
	public WebElement MoveToElement(WebElement element) {
		WaitUntilVisible(element);

		try {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			javascriptExecutor.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			doSoftAssertFailure("Failed to move to element");
		}

		return element;
	}

	public void retryingFindClick(WebElement element) {
		int maxRetries = 100;
		int attempts = 0;
		while (attempts < maxRetries) {
			try {
				WaitUntilEnabled(element).click();
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	/**
	 * Use JavascriptExecutor to scroll an element into view
	 * 
	 * @param element the element to scroll to
	 */
	public void ScrollToView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	
	/**
	 * Scroll the page up
	 */
	public void ScrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)");
	}
	
	public void ScrollToTop() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}



	/**
	 * Set the value of a text box
	 * 
	 * @param element   The textbox to set
	 * @param textToAdd The text to add
	 */
	public void SetTextBox(WebElement element, String textToAdd) {
		LoggerUtil.log("Fill text box " + element.toString() + " : " + textToAdd);

		WaitUntilVisible(element);

		MoveToElement(element).clear();

		try {
			element.click();
//			element.clear();
			ClearTextboxWithBackspace(element);
			element.sendKeys(textToAdd);
		} catch (Exception e) {
			doSoftAssertFailure(String.format("Could not set element text to '%s'", textToAdd));
		}

	}


	/**
	 * Helper to wait for an element to be fully available, preventing stale element
	 * exceptions
	 * 
	 * @param list of elements the element to wait for
	 * @return true/false
	 */
	public Boolean SteadyWait(final List<WebElement> element) {
		LoggerUtil.log("Waiting for element");

		String domWait = TestProperties.getProperty("domWait").toLowerCase();
		String steadyWait = TestProperties.getProperty("steadyWait").toLowerCase();

		if (domWait.equals("y"))
			DomWait();

		if (steadyWait.equals("y")) {
			return OverrideExpectedCondition(element.get(0));
		} else {
			return true;
		}
	}

	/**
	 * Helper to wait for an element to be fully available, preventing stale element
	 * exceptions
	 * 
	 * @param elements the element to wait for
	 * @return true/false
	 */
	public Boolean SteadyWait(final WebElement element) {
		LoggerUtil.log("Waiting for " + element.toString());

		String domWait = TestProperties.getProperty("domWait").toLowerCase();
		String steadyWait = TestProperties.getProperty("steadyWait").toLowerCase();

		if (domWait.equals("y"))
			DomWait();

		if (steadyWait.equals("y")) {
			return OverrideExpectedCondition(element);
		} else {
			return true;
		}
	}

	public Boolean OverrideExpectedCondition(WebElement element) {
		return waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				try {
					element.isDisplayed();
					return true;
				} catch (StaleElementReferenceException e) {
					doSoftAssertFailure("Failed waiting for element to be ready");
					return false;
				}
			}
		});
	}



	/**
	 * Verify an elements text value
	 * 
	 * @param element      the element to check
	 * @param expectedText the value to expect
	 */
	public void VerifyElementText(String actualElementText, String expectedText) {

		

		try {
			Assert.assertEquals(actualElementText, expectedText);
			ReportUtil.addScreenShot(LogStatus.PASS, "Expected: " + expectedText + ". Actual: " + actualElementText);
		} catch (AssertionError ae) {
			ReportUtil.addScreenShot(LogStatus.FAIL, "Expected: " + expectedText + ". Actual: " + actualElementText);
			ReportUtil.logMessage("Text Assertion Error Text", ae.getMessage());
			doSoftAssertFailure("Text Assertion Error Text not found "+ expectedText);
			//Assert.assertEquals(expectedText, actualElementText);
		}
	}

	/**
	 * Verify if two strings are equal
	 * 
	 * @param actual   The actual text value
	 * @param expected The expecyed text value
	 * @param message  The message to print
	 */
	public void VerifyEqual(String actual, String expected, String message) {
		if (actual.contains(expected)) {
			Assert.assertEquals(actual, expected);
			ReportUtil.addScreenShot(LogStatus.PASS, message);
		} else {
			ReportUtil.addScreenShot(LogStatus.FAIL, message);
		}
	}

	/**
	 * Wait until an element is clickable
	 * 
	 * @param element the element to wait for
	 * @return An instance of the element
	 */
	public WebElement WaitUntilClickable(WebElement element) {
		LoggerUtil.log("Wait until element is clickable - " + element);

		WaitUntilEnabled(element);

		try {
			this.waiter.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			doSoftAssertFailure("WaitUntilClickable -> Element did not become clickable");
		}

		return element;
	}

	private WebElement WaitUntilEnabled(WebElement element) {
		LoggerUtil.log("Wait until element is enabled - " + element);

		SteadyWait(element);

		try {
			this.waiter.until(v -> element.isEnabled() && element.isDisplayed());

		} catch (Exception e) {
			doSoftAssertFailure("WaitUntilPresent -> Element was not enabled");
		}

		return element;
	}



	/**
	 * Wait until an element is visible
	 * 
	 * @param element the element to wait for
	 * @return an instance of the webelement
	 */
	public WebElement WaitUntilVisible(WebElement element) {
		LoggerUtil.log("Wait until element is visible - " + element);

		SteadyWait(element);

		try {
			this.waiter.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			doSoftAssertFailure("WaitUntilVisible -> Element did not become visible");
		}

		return element;
	}

}
