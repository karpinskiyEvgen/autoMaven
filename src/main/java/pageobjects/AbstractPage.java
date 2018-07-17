package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user
 */
public abstract class AbstractPage {
	private WebDriver driver;
	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	/*************************
	 * Fields
	 *************************/

	@FindBy(tagName = "body")
	public WebElement body;

	@FindBy(tagName = "h1")
	public WebElement h1;

	@FindBy(tagName = "title")
	public WebElement title;

}
