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
	
	/**
	 * Creates a new project with the given project name
	 * @param projectName
	 */
	public void createProject(String projectName);
	
//	/**
//	 * Sets the project manager of the project indicated by projInfo to the developer indicated by devInfo
//	 * @param projInfo Project information for the project to modify
//	 * @param devInfo Developer information for the developer to make project manager
//	 */
//	public void setProjectManager(ProjectInfo projInfo, DeveloperInfo devInfo);
	
	/**
	 * Sets the project manager of the currently active project. If that user is not currently associated with the project,
	 * they are also added to the project. If they were an assistant developer, they are promoted to an ordinary developer
	 * @param devInfo The developer to be made the project manager
	 */
	public void setProjectManager(DeveloperInfo devInfo);
	
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
	
	/**
	 * Assigns a developer to an activity under a given project. The developer must already be assigned to another activity
	 * under the same project, or be in the list of unassigned developers
	 * @param dev Info for the developer to add
	 * @param proj Info for the project to modify
	 * @param act Info the activity under which the dev should be added
	 */
	public void assignDevToActivity(DeveloperInfo dev, ProjectInfo proj, ActivityInfo act);
	
	/**
	 * Gets the number of hours that the current user has registered on the active activity
	 * @param date The date to get the number of hours on
	 * @return The number of hours registered on the current activity by the current user. Returns 0 if no hours are registered
	 */
	public double getHoursOnActivity(Calendar date);
	
	/**
	 * Gets a developer from their initials
	 * @param initials The initials of the developer to get
	 * @return A DeveloperInfo object representing that developer, or null if no developer was found
	 */
	public DeveloperInfo getDeveloperWithInitials(String initials);
	
	/**
	 * Sets the developer with the given developer information to be an assistant dev. on the current activity
	 * @param dev Information on the developer to add to the current activity
	 */
	public void addAssistantDeveloper(DeveloperInfo dev);
	
	/**
	 * Removes an assistant developer from the currently active activity
	 * @param developerInfo The developer to remove
	 */
	public void removeAssistantDeveloper(DeveloperInfo developerInfo);

	/**
	 * Gets a list of information on all assistant developers associated with a project
	 * @return That list
	 */
	public List<DeveloperInfo> getAssistantDeveloperList();

	/**
	 * Returns information on the currently active activity
	 * @return That info
	 */
	public ActivityInfo getActiveActivity();

	
	/**
	 * Checks whether the current user is the project manager of the active project
	 * @return True if they are the project manager, false otherwise
	 */
	public boolean userIsProjectManager();
	
	
	/**
	 * Returns a list of all projects which the user has access to
	 * @return That list
	 */
	public List<ProjectInfo> getVisisbleProjects();
	
	/**
	 * Returns the number of hours budgeted on the currently active activity;
	 * @return
	 */
	public int getActivityTimeBudget();
	
	/**
	 * Sets the number of hours budgeted for the currently active activity
	 * @param input The number of hours to budget
	 */
	public void setActivityTimeBudget(int hours);
	
	/**
	 * Returns a list of all developers assigned to a given project
	 * @return
	 */
	public List<DeveloperInfo> getProjectDeveloperList();
	
	/**
	 * Adds a developer the the currently active project
	 * @param d The developer to add
	 */
	public void addDeveloperToProject(DeveloperInfo dev);
	
	/**
	 * Removes a developer from the currently active project
	 * @param devInfo The developer to remove 
	 */
	public void removeDeveloperFromProject(DeveloperInfo devInfo);
	
	/**
	 * Returns information on the project manager of the active project
	 * @return That information, or null if no project manager is set
	 */
	public DeveloperInfo getProjectManager();
	
	/**
	 * Returns a list of all developers associated with a given activity
	 * @return that list
	 */
	public List<DeveloperInfo> getActivityDeveloperList();
	
	/**
	 * Adds a developer to the active activity
	 * @param developerInfo The developer to add
	 */
	public void addDeveloperToActivity(DeveloperInfo developerInfo);
	
	/**
	 * Removes a develoepr from the active activity
	 * @param developerInfo The developer to remove
	 */
	public void removeDeveloperFromActivity(DeveloperInfo developerInfo);
	
	/**
	 * Returns a list of all available developers (defined as having fewer activities than their own maximum number)
	 * @return A list of all developers d, where d.activeActivities < d.maxActivities
	 */
	public List<DeveloperInfo> getAvailableDevelopers();
	
	/**
	 * Changes the start date of an activity to the given date
	 * @param date The start date
	 */
	public void setActivityStartDate(Calendar date);
	
	/**
	 * Changes the end date of an activity to the given date
	 * @param date The end date
	 */
	public void setActivityStopDate(Calendar date);
	
	/**
	 * Creates a new project with the given name, start date, stop date and project manager.
	 * @param name The name of the project
	 * @param startDate The start date of the project
	 * @param stopDate The stop date of the project
	 * @param projMan The project manager for the new project
	 */
	public void addNewProject(String name, Calendar startDate, Calendar stopDate, DeveloperInfo projMan);
	
	/**
	 * Creates a new activity under the current project with a given name, start date, stop date and project manager
	 * @param name The name of the activity
	 * @param startDate The start date of the project
	 * @param stopDate The stop date of the project
	 * @param hoursToBudget The number of hours to budget for the project
	 */
	public void addNewActivity(String name, Calendar startDate, Calendar stopDate, Integer hoursToBudget);
	
	/**
	 * Returns information on the currently active project
	 * @return
	 */
	public ProjectInfo getActiveProject();
	

}
