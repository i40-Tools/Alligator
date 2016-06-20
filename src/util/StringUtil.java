package util;



/**
 * This class is a support class for string util methods
 * @author igrangel
 *
 */
public class StringUtil {

	public static String ontExpressivity = "";
	
	/**
	 * Return the String with the first letter in lowercase
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirstChar(String str){
		return str.substring(0, 1).toLowerCase() + str.substring(1); 
	}
	
}
