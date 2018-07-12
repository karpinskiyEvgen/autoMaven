import base.BaseTestCase;
import org.testng.annotations.Test;
import services.UrlService;

/**
 * Created by user
 */
public class GeneralTestCase extends BaseTestCase {
	@Test
	public void test(){
		UrlService.getURL("", driver);
	}
}
