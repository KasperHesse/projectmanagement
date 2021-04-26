package schedulingapp;

import static ui.ControllerState.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class SchedulingApp {
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private Developer currentUser;
	private List<Developer> developerList;
	private List<Project> projectList;

	public SchedulingApp() {
		this.developerList = new ArrayList<Developer>();
		this.projectList = new ArrayList<Project>();
	}
	
	public void setCurrentUser(Developer currentUser) {
		this.currentUser = currentUser;
	}

	public void createProject(String name) {
		Project p = new Project(name);
		p.setApp(this);
		projectList.add(p);
		
	}
	
	public List<Developer> getDevelopers() {
		return this.developerList;
	}

	/**
	 * Checks whether the scheduling app contains any project with the given name.
	 * @param name The name of the project being checked against
	 * @return True if a project with exactly name {@code name} exists, otherwise false
	 */
	public boolean hasProjectNamed(String name) {
		return projectList.stream().anyMatch(p -> p.getName().equals(name));
	}

	/**
	 * Returns a handle to the project with a given name 
	 * @param name The name of the project to be found
	 * @return A handle to the project if it exists, otherwise throws an exception
	 * @throws NoSuchElementException if the project was not found
	 */
	public Project getProjectByName(String name) throws NoSuchElementException {
		List<Project> matches =  projectList.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		}
		throw new NoSuchElementException("This project does not exist");
	}

	/**
	 * Creates a developer with the given data and adds them to the list of develoeprs
	 * @param initials The initials of the developer
	 * @param name The full name of the developer
	 */
	public void createDeveloper(String initials, String name) {
		addDeveloper(new Developer(initials, name));
		
	}

	/**
	 * Returns a handle to the developer with the given set of initials
	 * @param initials The initials of the developer to be found
	 * @return A handle to the developer if any developer with those initials exists, null otherwisee
	 */
	public Developer getDeveloperByInitials(String initials) {
		List<Developer> matches = developerList.stream().filter(d -> d.getInitials().equals(initials)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Adds a developer to the list of developers registered with the application
	 * @param developer The developer to add
	 */
	public void addDeveloper(Developer developer) {
		developerList.add(developer);
	}

	/**
	 * Checks whether a developer with the given set of initials exists.
	 * @param initials The initials to be checked against
	 * @return True if a developer exists, false otherwise
	 */
	public boolean hasDevWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

	/**
	 * Returns a handle to the developer/user currently logged into the system
	 */
	public Developer getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Logs a user into the system, setting them as the current user
	 * @param initials The initials of the user to be logged in
	 * @return true if the login was succesful, false otherwise
	 */
	public boolean login(String initials) {
		Developer dev = getDeveloperByInitials(initials);
		if(dev == null) {
			return false;
		}
		this.setCurrentUser(dev);
		support.firePropertyChange("user", null, dev);
		return true;
	}
	
	public void logout() {
		support.firePropertyChange("user", currentUser, null);
		this.setCurrentUser(null);
		
	}
	
	public void addObserver(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}



	public Developer[] getAvailableDevelopers() {
		// TODO Auto-generated method stub
		return null;
	}

}
