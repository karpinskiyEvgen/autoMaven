package services;

/**
 * Created by user
 */
public class ObjectService {
	/**
	 * Method will replace several spaces for one
	 * @param text - original text
	 * @return
	 */
	public static String trimer (String text){
		return text.replaceAll("\\s+", " ");
	}
}
