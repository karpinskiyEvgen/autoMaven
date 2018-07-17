package pageobjects;

import org.openqa.selenium.WebDriver;

/**
 * Created by user
 */
public class SecondPage extends AbstractPage {
	private WebDriver driver;
	public SecondPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
}
