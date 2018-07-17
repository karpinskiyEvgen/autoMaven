package services;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by user
 */
@Log4j
public class ReportService {

	public static final String SCREENSHOTS_DIR = "target/screenshots/";

	static {
		Path path = Paths.get(SCREENSHOTS_DIR);
		if (Files.notExists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param testCaseName name of test (from name test class)
	 * @param message - text on screenshot
	 * @param driver
	 */
	public static void takeScreenshot(String testCaseName, String message, WebDriver driver) {

		try {
			String screenshotName = testCaseName + "ScreenShot.png";
			final BufferedImage image;
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			image=ImageIO.read(scrFile);
			Graphics g = image.getGraphics();
			g.setFont(new Font("Arial Black", Font.PLAIN, 20));
			g.setColor(Color.DARK_GRAY);
			g.drawString("URL: " + message, 50, 100);
			g.dispose();

			ImageIO.write(image, "png", new File(SCREENSHOTS_DIR + screenshotName));

			log.info("");
			log.warn("Screenshot captured.");
			log.warn("Screenshot name: \"" + screenshotName + "\".");
		}
		catch (WebDriverException | IOException e) {
			log.error("Catch " + e);
		}

	}

	/**
	 * @param condition bool condition
	 * @param errorMessage for logging
	 */
	public static void assertTrue(Boolean condition, String errorMessage ) {
		if (!condition){
			log.info("");
			log.error("Actual result:");
			log.error(errorMessage);
		}

		Assert.assertTrue(condition);

	}

	/**
	 * @param condition bool condition
	 * @param errorMessage for logging
	 */
	public static void assertFalse(Boolean condition, String errorMessage ) {
		if (condition){
			log.info("");
			log.error("Actual result:");
			log.error(errorMessage);
		}

		Assert.assertFalse(condition);

	}

	/**
	 * @param condition1 expected result
	 * @param condition2 actual result
	 * @param errorMessage for logging
	 * @param <T> - any object
	 */
	public static <T> void assertEquals(T condition1, T condition2, String errorMessage) {
		String error = "Actual result:\n"+errorMessage+"\nExpected: \"" + condition2 + "\", but found: \"" + condition1 + "\".";
		if (condition1 instanceof String){
			if (!((String) condition1).equalsIgnoreCase((String)condition2)) {
				log.error(error);
			}
		}
		else {
			if (!(condition1.equals(condition2))) {
				log.error(error);
			}
		}
		Assert.assertEquals(condition1, condition2);

	}

}