package com.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseFunctions extends TestBase{

	public void waitUntilElementFound(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * @author - kirankumar 
	 * @param - WebElement element, text to enter & Label
	 * @description - To enter text on webelement
	 */

	public void enterText(WebElement element, String textToEnter,String Label) {
		try {
			if(element.getAttribute("value").length()>0|| element.getText().length()>0){
				element.clear();
			}
			element.sendKeys(textToEnter);
		}catch (Exception e) {
			logFailed(e.getMessage().toString());
		}
	}
	/**
	 * @author - kirankumar 
	 * @param element
	 * @param textToEnter
	 * @param Label
	 * @description - To send text on webelement & press enter
	 */
	public void sendTextAndEnter(WebElement element, String textToEnter,String Label) {
		try {
			element.sendKeys(textToEnter+Keys.ENTER);
		}catch (Exception e) {
			logFailed(e.getMessage().toString());
		}
	}

	/**
	 * @author - kirankumar 
	 * @param - WebElement element, Label
	 * @throws Exception 
	 * @description - To click on webelement
	 */
	public Boolean click(WebElement element, String Label) throws Exception {
		try {
			waitUntilElementFound(element);
			element.click();
			logPassed("Able to click on "+Label);
			return true;
		}catch (Exception e) {
			logFailedWithScreenshot("Unable to click on "+ Label,Label);
			logFailed(e.getMessage().toString());
			return false;
		}
	}

	public String switchToLastTabWithOutURL() throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		int LastTab = tabs.size();
		driver.switchTo().window(tabs.get(LastTab - 1));
		Thread.sleep(2000);
		return driver.getCurrentUrl();
	}

	public Boolean switchToIframe(WebElement iframe) throws InterruptedException {
		try{
			driver.switchTo().frame(iframe);
			logPassed("Able to switch iframe");
			return true;
		}catch (Exception e) {
			logFailed(e.getMessage().toString());
			return false;
		}
	}
	
	public void switchToParentFrame() {
		 driver.switchTo().parentFrame();
	}

	public Boolean clickUsingActions(WebElement element,String Label) {
		try {
			Actions actionBuilder = new Actions(driver);
			actionBuilder.moveToElement(element).click(element).build().perform();
			return true;
		}catch (Exception e) {
			logFailed(e.toString());
		}
		return false;
	}

	public void logPassed(String passedLog) {
		try {
			test.pass(passedLog);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void logInfo(String info) {
		test.info(info);
	}

	public void logFailed(String failedLog) {

		test.fail(failedLog);
	}
	

	public void logException(Exception e) {
		logFailed(e.getMessage());
	}
	public void logFailedWithScreenshot(String failedlog,String label) throws Exception {
		String screenshotPath = getScreenshot(label);
		test.addScreenCaptureFromPath(screenshotPath);
		logFailed(failedlog);
	}

	public Boolean verifySearchTextInListOfWebElements(List<WebElement> lstOfWebElements,String expectedText) {
		List<Boolean> status = new ArrayList<Boolean>();
		for(WebElement webElement : lstOfWebElements) {
			if(webElement.getText().contains(expectedText)) 
				status.add(true);
		}
		if(status.size()==0)
			status.add(false);
		return status.stream().allMatch(val -> val == true);
	}

	public Boolean selectValueFromListOfWebElements(List<WebElement> lstOfWebElements,String expectedText) throws Exception {
		Boolean status = false;
		for(WebElement webElement : lstOfWebElements) {
			if(webElement.getText().equalsIgnoreCase(expectedText)) {
				status= click(webElement, "Expected Text");
				break;
			}
		}
		return status;
	}

	public boolean scrollToElement(WebElement ele, String desc) {
		Boolean status = false;
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			if (desc != null)
				logInfo("Scrolled to :" + desc);
			status = true;
			Thread.sleep(3000);
		} catch (Exception e) {
			logFailed("Unable to scroll"+desc+" "+e.getMessage());
		}
		return status;
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");
	}
	/**
	 * @author kirankumar 
	 * @description Check whether webelement is present or not
	 * @param element
	 * @return
	 */
	public static boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}

	public void moveToElementAndClick(WebElement webelement) {
		Actions objActions = new Actions(driver);
		objActions.moveToElement(new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(webelement)), 50, 0).click().build().perform();
	}

	/**
	 * @author kirankumar
	 * @description - to extract text from given webelement
	 * @param element
	 * @param textToEnter
	 * @param Label
	 * @return
	 */
	public String getText(WebElement element,String Label) {
		String text = "";
		try {
			text = element.getText();
			logPassed("Able to extract text from "+Label);
		}catch (Exception e) {
			logFailed("Unable to extract text from "+Label);
			logFailed(e.getMessage().toString());
		}
		return text;
	}

	/**
	 * @author kirankumar
	 * @param screenshotName
	 * @return
	 * @throws Exception
	 */
	public static String getScreenshot(String screenshotName) throws Exception {		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File source = takeScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public Set<String> getNoOfTabs_Windows() {
		return driver.getWindowHandles();
	}

	public void mouseHover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public Boolean dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			return true;
		}catch (Exception e) {
			logException(e);
			return false;
		}
	}
	
	public void navigateBack() throws InterruptedException {
		Thread.sleep(2000);
		driver.navigate().back();
	}
}
