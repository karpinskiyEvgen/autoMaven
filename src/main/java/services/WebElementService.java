package services;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static services.ObjectService.trimer;
import static services.ReportService.assertTrue;
import static services.UrlService.scrollDown;
import static services.UrlService.stopLoad;
import static services.WaiterService.waitForElementClickable;
import static services.WaiterService.waitForElementVisible;

/**
 * Created by user by user
 */
@Log4j
public class WebElementService {

	/**
	 * Move mouse to coordinate
	 * @param x
	 * @param y
	 * @param driver
	 */
	public static void moveToCoordinate(int x, int y, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveByOffset(x, y).build().perform();
		log.info("Move to coordinate "+x+"x"+y);
	}

	/**
	 * Method wait that element will be clickable and doing click
	 * @param element
	 * @param elementName
	 * @param driver
	 */
	public static void clickOnElement(WebElement element, String elementName, WebDriver driver) {
		waitForElementVisible(element, driver);
		try {
			element.click();
			log.info("Click on \"" + elementName + "\".");
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "\"" + elementName + "\" was not found on page after timeout.");
		}
		catch (ElementNotVisibleException e) {
			log.error("ElementNotVisibleException");
			clickHack(element, elementName, driver);
		}
		catch (TimeoutException e) {
			stopLoad(driver);
		}
		catch (StaleElementReferenceException e) {
			log.warn("StaleElementReferenceException.");
			log.info("Click on \"" + elementName + "\".");
			element.click();
		}
		catch (WebDriverException e) {
			log.error("WebDriverException" + e);
			clickHack(element, elementName, driver);
		}
	}

	/**
	 * Click hack - scroll down page if element is invisible (in bottom page)
	 * @param element
	 * @param elementName
	 * @param driver
	 */
	private static void clickHack(WebElement element, String elementName, WebDriver driver) {
		boolean flag = true;
		int attempt = 0;

		while (flag && attempt < 5) {
			attempt++;
			try {
				log.info("\"" + elementName + "\" is hide by another element, move down...");
				scrollDown(driver);
				moveToCoordinate(0, 0, driver);
				element.click();
				log.info("Click on \"" + elementName + "\".");
				flag = false;
			} catch (WebDriverException ignored) {
			}
		}
	}

	/**
	 * Method will return text from element.
	 * @param element
	 * @param elementName
	 * @return String (text from element)
	 */
	public static String getElementText(WebElement element, String elementName) {

		try {
			String text = element.getText();
			log.info("\"" + elementName + "\" content on page  - \"" + trimer(text) + "\".");
			return text;
		}
		catch (NoSuchElementException | ElementNotVisibleException e) {
			assertTrue(false, "\"" + elementName + "\" was not found on page after timeout.");
			throw new CustomException(e.toString());
		}
	}

	/**
	 * Input text to field
	 * @param element of field
	 * @param elementName
	 * @param inputText
	 */
	public static void inputText(WebElement element, String elementName, String inputText) {
		try {
			element.sendKeys(inputText);
			log.info("\"" + elementName + "\" input text: \"" + inputText + "\".");
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "\"" + elementName + "\" was not found on page after timeout.");
		}
		catch (ElementNotVisibleException e) {
			assertTrue(false, "\"" + elementName + "\" was not visible.");
		}

	}

	/**
	 * Input text to field (before will be clear field)
	 * @param element of field
	 * @param elementName
	 * @param inputText
	 * @param driver
	 */
	public static void inputTextClear(WebElement element, String elementName, String inputText, WebDriver driver) {

		try {
			waitForElementVisible(element, driver);
			int attempt = 0;
			element.clear();
			element.sendKeys(inputText);
			while (element.getAttribute("value").length() != inputText.length() && attempt < 5) {
				attempt++;
				element.clear();
				element.sendKeys(inputText);
			}
			log.info("\"" + elementName + "\" input text: \"" + inputText + "\".");
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "\"" + elementName + "\" was not found on page after timeout.");
		}
		catch (ElementNotVisibleException e) {
			assertTrue(false, "\"" + elementName + "\" was not visible.");
		}
		catch (InvalidElementStateException e) {
			log.warn("Catch InvalidElementStateException.");
			waitForElementClickable(element, driver);
			element.clear();
			element.sendKeys(inputText);
		}
	}

	/**
	 * Select checkbox
	 * @param element
	 * @param elementName
	 * @param driver
	 */
	public static void selectCheckBox(WebElement element, String elementName, WebDriver driver) {
		if (!checkElementSelected(element, elementName)) {
			clickOnElement(element, elementName, driver);
			log.info("Element " + elementName + " is selected.");
		} else {
			log.info("Element " + elementName + " is already selected.");
		}
	}

	/**
	 * Deselect checkbox
	 * @param element
	 * @param elementName
	 * @param driver
	 */
	public static void deSelectCheckBox(WebElement element, String elementName, WebDriver driver) {
		if (checkElementSelected(element, elementName)) {
			clickOnElement(element, elementName, driver);
			log.info("Element " + elementName + " is deselected.");
		}
		else {
			log.info("Element " + elementName + " is already deselected.");
		}
	}

	/**
	 * Verify that checkbox is selected
	 * @param element
	 * @param elementName
	 * @return bool value
	 */
	public static boolean checkElementSelected(WebElement element, String elementName) {
		try {
			//Check WebElement is selected.
			log.info("Verify \"" + elementName + "\" is selected.");
			if (element.isSelected()) {
				log.info("\"" + elementName + "\" is selected.");
				return true;
			}
			else {
				log.info("\"" + elementName + "\" is not selected.");
				return false;
			}
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "Element " + elementName + " was not found on page after timeout.");
			return false;
		}
	}

	/**
	 * Select drop box by text
	 * @param element
	 * @param text
	 */
	public static void selectDropBoxByText(WebElement element, String text) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(text);
			log.info("Select \"" + text + "\".");
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "\"" + text + "\" is missing!");
		}
	}

	/**
	 * Deselect drop box by text
	 * @param element
	 * @param text
	 */
	public static void deSelectDropBoxByText(WebElement element, String text) {
		try {
			Select select = new Select(element);
			select.deselectByVisibleText(text);
			log.info("Deselect \"" + text + "\".");
		}
		catch (NoSuchElementException e) {
			assertTrue(false, "\"" + text + "\" is missing!");
		}
	}

}
