package ui;

import java.text.*;
import java.util.Calendar;
import java.util.NoSuchElementException;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class Utils {
	
	/**
	 * Verifies whether a string matches a valid date in the format "yyyy-MM-dd" (eg. 2021-12-24)
	 * @param date The date string
	 * @return A calendar object representing that date, or null if the string is not valid
	 */
	public static Calendar validateDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		Calendar cal;
		try {
			cal = Calendar.getInstance();
			cal.setTime(formatter.parse(date));
		} catch(ParseException e) {
			cal = null;
		}
		return cal;
	}
	
	/**
	 * Verifies that a string matches a valid integer in a given range [0;max]
	 * @param myint The string representing an integer
	 * @param max The maximum value (inclusive)
	 * @return The value if inside the allowed range, Integer.MIN_VALUE otherwise
	 */
	public static int validateInteger(String myint, int max) {
		int tokenInt;
		try {
			tokenInt = Integer.parseInt(myint);
			if(tokenInt < 0 || tokenInt > max) {
				tokenInt = Integer.MIN_VALUE;
			}
		} catch(NumberFormatException e) {
			tokenInt = Integer.MIN_VALUE;
		}
		return tokenInt;
	}

	/**
	 * Verifies that a string matches a valid double in given range [0;max]
	 * @param value The string representing a double
	 * @param max The maximum value (inclusive)
	 * @return The value if inside the allowed range, Integer.MIN_VALUE otherwise
	 */
	public static double validateDouble(String value, double max) {
		return validateDouble(value, 0, max);
	}
	/**
	 * Verifies that a string matches a valid double in given range [min;max]
	 * @param value The string representing a double
	 * @param max The maximum value (inclusive)
	 * @param min The minimum value (inclusive)
	 * @return The value if inside the allowed range, Integer.MIN_VALUE otherwise
	 */
	public static double validateDouble(String value, double min, double max) {
		double token;
		try {
			token = Double.parseDouble(value);
			token = Math.round(2.0*token)/2.0; //Round to nearest 0.5
			if(token < min || token > max) {
				token = (double) Integer.MIN_VALUE;
			}
		} catch(NumberFormatException e) {
			token = (double) Integer.MIN_VALUE;
		}
		return token;
	}
	
	/**
	 * Searches a string of the type {@code <key>=<value>} for a given key, and returns the value following that key
	 * @param str The string to search in
	 * @param key The key to search for in the string
	 * @return The value found after that key, if the key was present
	 * @throws IllegalArgumentException If the string is not of the type {@code <key>=<value>}
	 */
	public static String findValueAfterKey(String str, String key) throws NoSuchElementException, IllegalArgumentException {
		String str2 = str.replace("\"", "");
		String matchString = String.format("%s=.+", key);
		if(!str2.matches(matchString)) { //key, followed by =, followed by anything
			throw new IllegalArgumentException("Input string '" + str + "' was not parseable");
		}
		String valueStr = str2.substring(str2.indexOf("=")+1);
		return valueStr;	
	}
	

}
