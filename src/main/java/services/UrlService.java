package services;


import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;

import java.util.List;

import static services.Constants.PAGE_TIMEOUT;
import static services.Constants.conf;
import static services.ReportService.assertTrue;
import static services.WaiterService.pageLoaderWait;
import static services.WaiterService.sleep;
import static services.WebElementService.moveToCoordinate;

/**
 * Created by user
 */
@Log4j
public class UrlService {

    public static void getURL(String url, WebDriver driver) {
        log.info("Navigate to \""+conf.url()+url+"\"...");
        driver.get(conf.url()+url);
        pageLoaderWait(driver, PAGE_TIMEOUT);
        log.info("Navigate to \"" + conf.url()+url + "\" finished.");
    }

    public static void getDirectlyURL(String url, WebDriver driver) {
        driver.getCurrentUrl();
        log.info("Navigate to \""+url+"\"...");
        driver.get(url);
		pageLoaderWait(driver, PAGE_TIMEOUT);
        log.info("Navigate to \"" + url + "\" finished.");
        moveToCoordinate(0,0,driver);
    }


    public  static void refreshPage(WebDriver driver){
        try {
            driver.navigate().refresh();
            log.info("Page was refreshed.");
        }
        catch (WebDriverException e){
            stopLoad(driver);
        }
    }

    public static void switchToFrame(int frameIndex, WebDriver driver){
        List<WebElement> frames = driver.findElements(By.tagName("frame"));
        int attemptCounter = 0;
        while (frames.size()<=frameIndex){
            frames = driver.findElements(By.tagName("frame"));
            sleep(1);
            attemptCounter++;
            if (attemptCounter == 10){
                assertTrue(false,"Invalid index of frame");
            }
        }

        driver.switchTo().frame(frames.get(frameIndex));
        log.info("Switch to "+frameIndex+"-index frame.");

    }

    public static void switchToIframe(int frameIndex, WebDriver driver){
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        int attemptCounter = 0;
        while (frames.size()<=frameIndex){
            frames = driver.findElements(By.tagName("iframe"));
            sleep(1);
            attemptCounter++;
            if (attemptCounter == 10){
                assertTrue(false,"Invalid index of iframe");
            }
        }

        driver.switchTo().frame(frames.get(frameIndex));
		log.info("Switch to "+frameIndex+"-index iframe.");

    }

    public static void navigateBack (WebDriver driver){
        try {
            driver.navigate().back();
			log.info("Returned to the previous page.");
        }
        catch (TimeoutException e){
            stopLoad(driver);
        }
    }

    public static void scrollDown(WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
    }


    public static void stopLoad(WebDriver driver){
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        log.info("Timeout on loading page \""+driver.getCurrentUrl()+"\".");
    }


    public static void resizeWindow(Dimension dimension, WebDriver driver){
        driver.manage().window().setSize(dimension);
        log.info("Resize window to "+dimension+" size.");
    }

}
