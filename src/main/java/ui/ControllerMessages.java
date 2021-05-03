package ui;

/**
 * Messages that the controller may pass to the view for display to the end user.
 * All messages prefixed with "E" are error messages, messages prefixed with "I" are info messages
 * @author kaspe
 *
 */
public class ControllerMessages {
	public final static String E_INVALID_INPUT = "Unable to understand input. Please type one of the numbers from the list\n"
			+ "To view the list of options again, type \"help\"\n"
			+ "To go back, type \"back\"";
	
	public final static String E_ENTER_INITIALS = "Please enter your initials (4 characters only)";
	
	public final static String E_NO_SUCH_INITIALS = "No user with those initials is registered";
	
	public final static String I_REGISTER_TIME = "To register time, enter a date in format YYYY-mm-DD\n"
			+ "and the number of hours to register (resolution 1/2 hour)\n"
			+ "Example: 2021-12-24 12.5";
	
	public final static String E_INVALID_DATE = "Unable to parse date. Dates must be in format yyyy-MM-dd (eg. 2021-12-24)";
	
	public final static String E_INVALID_HOURS = "Unable to parse hours. Must be at least 0.5 and at most 24";

	public static final String I_EDIT_REGISTERED_TIME = "To register your edited time, enter a date in format YYYY-mm-DD\n"
			+ "and the number of hours to modify that value by (resolution 1/2 hours)\n"
			+ "Example: 2021-12-24 2 (if previous time registered was 12, new time will be 14)";
}
