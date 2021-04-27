package ui;

import java.text.*;
import java.util.Calendar;

public class Utils {
	
	/**
	 * Verifies whether a string matches a valid date in the format "yyyy-MM-dd" (eg. 2021-12-24)
	 * @param date The date string
	 * @return A calendar object representing that date, or null if the string is not valid
	 */
	public static Calendar validateDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
	 * @return The value if inside the allowed range, -1 otherwise
	 */
	public static int validateInteger(String myint, int max) {
		int tokenInt;
		try {
			tokenInt = Integer.parseInt(myint);
			if(tokenInt < 0 || tokenInt > max) {
				tokenInt = -1;
			}
		} catch(NumberFormatException e) {
			tokenInt = -1;
		}
		return tokenInt;
	}

	/**
	 * Verifies that a string matches a valid double in given range [0;max]
	 * @param value The string representing a double
	 * @param max The maximum value (inclusive)
	 * @return The value if inside the allowed range, -1 otherwise
	 */
	public static Double validateDouble(String value, double max) {
		Double token;
		try {
			token = Double.parseDouble(value);
			token = Math.round(2.0*token)/2.0; //Round to nearest 0.5
		} catch(NumberFormatException e) {
			token = -1.0;
		}
		return token;
	}

}
