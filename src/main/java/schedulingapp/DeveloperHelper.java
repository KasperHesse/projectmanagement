package schedulingapp;

/**
 * This class is intended to make Developers more manageable when writing tests
 * @author Kasper Hesse, s183735
 *
 */
public class DeveloperHelper {
	
	SchedulingApp schedulingApp;
	
	public DeveloperHelper(SchedulingApp schedulingApp) {
		this.schedulingApp = schedulingApp;
	}

	/**
	 * Returns a handle to the developer with the given initials. If no such developer exists already,
	 * creates the developer and returns a handle
	 * @param initials The initials of the developer to get
	 * @return The initials of the developer
	 */
	public Developer getDeveloper(String initials) {
		if(schedulingApp.hasDevWithInitials(initials)) {
			return schedulingApp.getDeveloperByInitials(initials);
		}
		schedulingApp.addDeveloper(new Developer(initials, "auto generated with initials" + initials));
		return schedulingApp.getDeveloperByInitials(initials);

	}

}
