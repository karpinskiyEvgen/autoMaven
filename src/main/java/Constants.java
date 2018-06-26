import org.openqa.selenium.Dimension;
import services.PropertyReader;

/**
 * Created
 */
public class Constants {
	public static PropertyReader propertyReader = new PropertyReader("properties/app.properties");

	//***************************** TIMEOUTS *************************************************
	public static final int PAGE_TIMEOUT = 60;
	public static final int ELEMENT_TIMEOUT = 20;

	//***************************** DIMENSIONS *************************************************
	public static final Dimension FULL_HD = new Dimension(1920, 1080);
	public static final Dimension WXGA = new Dimension(1360, 768);
	public static final Dimension XGA = new Dimension(1024, 768);
}
