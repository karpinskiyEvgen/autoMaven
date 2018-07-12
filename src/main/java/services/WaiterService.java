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

    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitPageLoader(String url, WebDriver driver) {
        waitPageLoader(url, PAGE_TIMEOUT, driver);
    }

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

}
