package services;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static services.Constants.ELEMENT_TIMEOUT;
import static services.Constants.PAGE_TIMEOUT;
import static services.ReportService.assertTrue;
import static services.UrlService.stopLoad;

/**
 * @author Created by user
 */
@Log4j
public class WaiterService {

    /**
     * @param text which need to wait
     * @param element
     * @param driver
     */
    public static void waitForTextVisible(String text, WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,ELEMENT_TIMEOUT);
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
            log.info("TExt: \"" + text +"\" is present.");
        }
        catch (TimeoutException e){
            assertTrue(false, "Text: \"" + text + "\" is not presents.");
        }
        catch (NoSuchElementException e){
            assertTrue(false, "This element not found.");
        }
    }

    /**
     * Method will wait load page (page load complete)
     * @param driver
     * @param timeout
     */
    public static void pageLoaderWait(WebDriver driver, int timeout) {
        log.info("Wait " + timeout + " seconds for page to load...");
        try {
            new WebDriverWait(driver, timeout)
                    .withMessage("Page was not loaded after timeout.")
                    .until((ExpectedCondition<Boolean>) d ->
                            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        }
        catch (WebDriverException e) {
            log.warn(e);
        }
    }

    /**
     * @param element which need to wait
     * @param driver
     */
    public static void waitForElementVisible(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,ELEMENT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(element));
        }
        catch (TimeoutException e){
            assertTrue(false, "Element: \"" + element + "\" is not presents.");
        }
        catch (StaleElementReferenceException e){
			log.warn(("Element: \"" + element + "\" is not found in the cache."));
        }
    }

    /**
     * @param seconds - pause in a test
     */
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param url - contains url which need to wait (default timeout)
     * @param driver
     */
    public static void waitPageLoader(String url, WebDriver driver) {
        waitPageLoader(url, PAGE_TIMEOUT, driver);
    }

    /**
     * @param url - contains url which need to wait
     * @param seconds - timeout
     * @param driver
     */
    public static void waitPageLoader(String url, int seconds, WebDriver driver) {
        try {
            log.info("Waiting for \"" + url + "\" page...");
			(new WebDriverWait(driver, seconds))
					.withMessage("Url is not appear on element after timeout.")
					.until(((ExpectedCondition<Boolean>) d -> driver.getCurrentUrl().contains(url)));
        }
        catch (TimeoutException e) {
            stopLoad(driver);
        }
	}

    /**
     * @param element which need to wait
     * @param driver
     */
    public static void waitForElementClickable(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,ELEMENT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        catch (TimeoutException e){
            assertTrue(false, "Element: \"" + element + "\" is not clickable.");
        }
        catch (StaleElementReferenceException e){
            log.warn(("Element: \"" + element + "\" is not found in the cache."));
        }
    }

}
