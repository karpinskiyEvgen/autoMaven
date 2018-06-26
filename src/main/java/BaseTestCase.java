import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import services.TestConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static services.ReportService.takeScreenshot;
import static services.WebElementService.moveToCoordinate;

/**
 * Created
 */
@Log4j
public class BaseTestCase {

	public static TestConfig conf = initTestConfig();
	public WebDriver driver;
	private ThreadLocal<RemoteWebDriver> drivers = new ThreadLocal<>();
	public static String server="localhost";
	public static int port=4444;
	protected boolean selenoidEnabled = Boolean.parseBoolean(Constants.propertyReader.getAnyVal("selenoidEnabled"));
	protected String browserName = Constants.propertyReader.getAnyVal("browserName");
	protected String testCaseName = this.getClass().getSimpleName();

	/**
	 * @return string for connecting to grid, for example - http://localhost:4444/wd/hub
	 */
	public String getGrid(){
		return "http://" + server + ":" + port + "/wd/hub";
	}

	/**
	 * @return driver for using into testing scenario
	 */
	public WebDriver getDriver(){
		return driver;
	}

	/**
	 * Steps before start suite
	 */
	@BeforeSuite(alwaysRun = true)
	public void startTestSuite() {
		System.setProperty("java.util.logging.config.file", "logging.properties");
		log.info("");
		log.info("=================================================================");
		log.info("Test suite started.");
		log.info("=================================================================");
		log.info("");
		log.info("Grid url: " + getGrid());
		log.info("Test environment url: \"" + System.getProperty("environment") + "\".");
		log.info("");

	}

	/**
	 * Initial config
	 * @return test config with main urls for testing
	 */
	public static TestConfig initTestConfig() {
		String env = Constants.propertyReader.getAnyVal("environment").toLowerCase();
		System.setProperty("conf_file", env);
		log.info("Config file - " + System.getProperty("conf_file"));
		return ConfigFactory.create(TestConfig.class, System.getProperties(), System.getenv());
	}

	protected DesiredCapabilities initCapabilities() {

		//List of browsers.
		DesiredCapabilities capabilities;
		switch(browserName) {
			case "chrome":
				capabilities = DesiredCapabilities.chrome();
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				prefs.put("profile.default_content_setting_values.notifications", 2);

				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);

				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				break;
			case "firefox":
				capabilities = DesiredCapabilities.firefox();
				break;
			case "safari":
				capabilities = DesiredCapabilities.safari();
				break;
			default:
				capabilities = DesiredCapabilities.chrome();
				break;
		}
		if (selenoidEnabled) {
			capabilities.setCapability("enableVNC", true);
			capabilities.setBrowserName(browserName);
		}

		return capabilities;
	}

	@BeforeTest
	public void startTest(String browserName) throws MalformedURLException {

		drivers.set(new RemoteWebDriver(new URL(getGrid()), initCapabilities()));

		drivers.get().manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT, TimeUnit.SECONDS);
		drivers.get().manage().timeouts().pageLoadTimeout(Constants.PAGE_TIMEOUT, TimeUnit.SECONDS);
		drivers.get().manage().window().setSize(Constants.FULL_HD);
		moveToCoordinate(0, 0, drivers.get());
		driver = drivers.get();

	}

	@AfterTest
	public void finishTest() {
		try {
			takeScreenshot(testCaseName, getDriver().getCurrentUrl(), getDriver());
			getDriver().quit();
			log.info("Close browser.");
		}
		catch (NoSuchWindowException | NoSuchSessionException e) {
			log.info("");
			log.error("No such window.");
			log.error("TestCase: \"" + testCaseName + "\" FAILED.");
			log.info("");
		}
		catch (WebDriverException e) {
			log.warn("Session was terminated");
		}

		//Define TC finish in console log.
		log.info("=================================================================");
		log.info("TestCase: \"" + testCaseName + "\" finished.");
		log.info("=================================================================");
		log.info("");

	}

	@AfterSuite(alwaysRun = true)
	public void finishTestSuite() {
		log.info("=================================================================");
		log.info("Test suite finished.");
		log.info("=================================================================");
		log.info("");
	}

}
