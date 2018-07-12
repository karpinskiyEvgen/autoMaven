package services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static services.EncodingService.convertUTF;

/**
 * Created by user
 */
public class PropertyReader {
	public Properties properties;

	/**
	 * Load property object from file
	 * @param file path to file
	 */
	public PropertyReader(String file){
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
		try {
			properties = new Properties();
			if (file.contains(".properties")){
				properties.load(inputStream);
			}
			else {
				properties.load(new InputStreamReader(inputStream, "UTF-8"));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get value by key
	 * @param key string
	 * @return value by key
	 */
	public String getValue(String key){
		return properties.getProperty(key)==null ? null : convertUTF(properties.getProperty(key));
	}

	public String getAnyVal(String key){
		return System.getProperty(key)!=null ? System.getProperty(key)
				: getValue(key);
	}


}
