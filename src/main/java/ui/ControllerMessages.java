package ui;

/**
 * Messages that the controller may pass to the view for display to the end user.
 * All messages prefixed with "E" are error messages, messages prefixed with "I" are info messages
 * @author kaspe
 *
 */
public class ControllerMessages {
	public final static String E_INVALID_INPUT = "Unable to understand input. Please type one of the numbers from the list\n"
			+ "To go back, type \"back\"";
	
	public final static String E_ENTER_INITIALS = "Please enter your initials (4 characters only)";
	
	public final static String E_INVALID_INITIALS = "No user with those initials is registered in the system\n"
			+ "Make sure to enter 4 characters for the initials";
	
	public final static String E_INVALID_DATE = "Unable to parse date. Dates must be in format yyyy-MM-dd (eg. 2021-12-24)";
	
	public final static String E_INVALID_HOURS = "Unable to parse hours. Must be at least 0.5 and at most 24";
	
	public final static String E_INVALID_BUDGET = "Unable to parse hours budget. Please enter a number greater than 1";
	
	public final static String E_INVALID_ASSIST = "This developer cannot assist as they have reached the max number of projects";
	
	public static final String E_NOT_PROJ_MAN = "You cannot do this, as you are not the project manager";
	
	public final static String I_REGISTER_TIME = "To register time, enter a date in format YYYY-mm-DD\n"
			+ "and the number of hours to register (resolution 1/2 hour)\n"
			+ "Example: 2021-12-24 12.5";
	
	public final static String I_WELCOME_MSG = "Welcome to the Scheduling Application. Please enter your initials to login";

	public final static String I_HELP_MSG = "To go back to the previous menu, type \"back\"\n"
			+ "To go back to the first menu, type \"home\"";

	public static final String I_EDIT_REGISTERED_TIME = "To register your edited time, enter a date in format YYYY-mm-DD\n"
			+ "and the number of hours to modify that value by (resolution 1/2 hours)\n"
			+ "Example: 2021-12-24 2 (if previous time registered was 12, new time will be 14)";
	
	public static final String I_VIEW_TIME = "To view the number of hours you have registered on a given date\n"
			+ "enter the date in the format YYYY-mm-DD (eg 2021-12-24)";
	
	public static final String I_SEEK_ASSISTANCE = "To seek assistance, enter the initials of the developer to assist on the project";
	
	public static final String I_REMOVE_ASST_DEV = "To remove an assistant developer, enter a number from the list below";
	
	public static final String I_CHANGE_BUDGET_HOURS = "Please enter the number of hours you would like to budget for this activity";
	
	public static final String I_ADD_DEV_ACT = "To add a developer to this activity, select them from the list below";
	
	public static final String I_REMOVE_DEV_ACT = "To remove a developer from this activity, select them from the list below";
	
	public static final String I_CHANGE_START_WEEK = "To change the start date of this project, enter a new date below";
	
	public static final String I_CHANGE_STOP_WEEK = "To change the end date of this project, enter a new date below";
	
	public static final String I_ADD_DEV_PROJ = "To add a developer to this project, enter their initials below";
	
	public static final String I_REMOVE_DEV_PROJ = "To remove a developer from this project, select them from the list below";
	
	public static final String I_CHANGE_PROJ_MAN = "Enter the initials of the developer to be made the PM of this project";
	
	public static final String I_NO_DEVELOPERS_REMOVE = "No developers can be removed from this activity";
	
	public static final String I_NO_DEVELOPERS_ADD = "No developers can be added to this activity";
	
	public static final String I_VIEW_DEVS = "All developers shown below are available\n"
			+ "To add them to a project, navigate to Show Projects -> <your project> -> Add Developer -> <enter their initials>";
	

}
