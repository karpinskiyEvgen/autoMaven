package services;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${conf_file}.properties", "classpath:general.properties"})
public interface TestConfig extends Config {

	@Config.DefaultValue("https://www.templatemonsterdev.com/")
	String url();

}
