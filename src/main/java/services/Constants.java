package services;

import org.openqa.selenium.Dimension;

import static base.BaseTestCase.initTestConfig;

/**
 * Created by user
 */
public class Constants {

	public static TestConfig conf = initTestConfig();

	//***************************** TIMEOUTS *************************************************
	public static final int PAGE_TIMEOUT = 60;
	public static final int ELEMENT_TIMEOUT = 20;

	//***************************** DIMENSIONS *************************************************
	public static final Dimension FULL_HD = new Dimension(1920, 1080);
	public static final Dimension XGA = new Dimension(1024, 768);
}
