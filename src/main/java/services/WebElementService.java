package services;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by user by user
 */
@Log4j
public class WebElementService {

	public static void moveToCoordinate(int x, int y, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveByOffset(x, y).build().perform();
		log.info("Move to coordinate "+x+"x"+y);
	}
}
