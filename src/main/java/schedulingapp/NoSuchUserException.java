package schedulingapp;

/**
 * This exception is thrown if an attempt is made to login with a set of initials that do not match a user in the system.
 * @author Kasper Juul Hesse Rasmussen, s183735
 *
 */
public class NoSuchUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7124933858592855353L;

	public NoSuchUserException(String s) {
		super(s);
	}
	
}
