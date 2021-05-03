package schedulingapp;

import dto.*;

import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * This is the publicly available interface, used by a controller to interact with the scheduling application
 * @author Kasper Hesse, s183735
 *
 */
public interface ControllerInterface {
	
	/**
	 * Gets a handle of all activities linked to the currently active project
	 * @return A list of activity information
	 */
	public List<ActivityInfo> getActiveProjectActivities();
	
	/**
	 * Registers time on the currently active activity
	 * @param date A calendar object representing the date to register time on
	 * @param hours The number of hours to register on that date
	 */
	public void registerTimeOnActivity(Calendar date, double hours);
	
	/**
	 * Creates a new developer in the system. 
	 * @param name the name of the developer
	 * @param initials The initials of the developer (must be exactly 4 characters)
	 * @return a handle to a DeveloperInfo object with that data
	 */
	public DeveloperInfo createDeveloper(String name, String initials);
	
	/**
	 * Logs a user into the system, setting them as the current user
	 * @param initials The initials of the user to be logged in
	 * @return true if the login was succesful, false otherwise
	 */
	public boolean login(String initials);
	
	/**
	 * Logs the current user out of the system. If no user is logged in, does nothing
	 */
	public void logout();
	
	public ProjectInfo createProject(String projectName);
	
	/**
	 * Sets the project manager of the project indicated by projInfo to the developer indicated by devInfo
	 * @param projInfo Project information for the project to modify
	 * @param devInfo Developer information for the developer to make project manager
	 */
	public void setProjectManager(ProjectInfo projInfo, DeveloperInfo devInfo);
	
	/**
	 * Creates an activity under the project indicated by projInfo
	 * @param projInfo The project under which to create an activity
	 * @param name The name of the activity to be created
	 * @return A handle to an ActivityInfo holding information for that activity
	 */
	public ActivityInfo createActivity(ProjectInfo projInfo, String name);
	
	/**
	 * Returns a list with information on all projects currently in the system
	 * @return That list
	 */
	public List<ProjectInfo> getAllProjects();
	
	/**
	 * Adds an observer (property change listener) to the model
	 * @param listener The listener to add
	 */
	public void addObserver(PropertyChangeListener listener);
	
	/**
	 * Sets the currently active project based on the information stored in the ProjectInfo parameter
	 * @param pi The project info object 
	 * @throws NoSuchElementException If no project matches the information passed in the parameter
	 */
	public void setActiveProject(ProjectInfo projInfo) throws NoSuchElementException;
	
	/**
	 * Sets the currently active activity, based on the information stored in the parameter. 
	 * The activity must exist under the currently active project
	 * @param actInfo The information describing the activity
	 * @throws NoSuchElementException if the given ActivityInfo does not match any activity under the currently
	 * active project
	 */
	public void setActiveActivity(ActivityInfo actInfo) throws NoSuchElementException;
	
	/**
	 * Edits the time registered on an activity by adding the offset to the previously stored value
	 * @param date The date on which to edit registered time
	 * @param hours The number of hours to modify the edited time by
	 */
	public void editTimeOnActivity(Calendar date, double hours);
}
