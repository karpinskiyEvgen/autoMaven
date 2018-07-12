package services;

import lombok.extern.log4j.Log4j;

import java.io.UnsupportedEncodingException;

/**
 * Created by user
 */
@Log4j
public class EncodingService {

	/**
	 * Method will convert string to utf-8
	 * @param origin string which will be converted
	 * @return string of utf-8
	 */
	public  static String convertUTF(String origin){
		String convert = null;
		try {
			convert = new String(origin.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (UnsupportedEncodingException e){
			log.error(e.toString());
		}

		return convert;
	}
}
