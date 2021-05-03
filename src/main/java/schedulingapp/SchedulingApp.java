package schedulingapp;

import java.text.ParseException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import dto.*;

public class SchedulingApp implements ControllerInterface {
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private Developer currentUser;
	private List<Developer> developerList;
	private List<Project> projectList;
	
	private Project activeProject;
	private Activity activeActivity;

	public SchedulingApp() {
		this.developerList = new ArrayList<Developer>();
		this.projectList = new ArrayList<Project>();
		this.activeProject = null;
	}
	
	/**
	 * Sets the current user of the schedulingApp
	 * @param currentUser The user currently logged in to the application
	 */
	public void setCurrentUser(Developer currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param startDate The given date the project is expected to begin, as Calendar object
	 * @param stopDate The given date the project is expected to end, as Calendar object
	 * @param projectManager The developer who is to manage the project
	 */
	public void createProject(String projectName, Calendar startDate, Calendar stopDate, Developer projectManager) {
		Project p = new Project(projectName, startDate, stopDate, projectManager, this);
//		p.setApp(this);
		projectList.add(p);
	}
	
	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param startDate The given date the project is expected to begin, as Calendar object
	 * @param stopDate The given date the project is expected to end, as Calendar object
	 */
	public void createProject(String projectName, Calendar startDate, Calendar stopDate) {
		createProject(projectName, startDate, stopDate, null);
	}
	
	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param startDate The given date the project is expected to begin, as a String formatted as "yyyy-MM-dd"
	 * @param stopDate The given date the project is expected to end, as a String formatted as "yyyy-MM-dd"
	 * @throws ParseException If the date-Strings are not correctly formatted
	 */
	public void createProject(String projectName, String startDate, String stopDate) throws ParseException {
		//TODO Catch this exception
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar startCal = Calendar.getInstance();
		Calendar stopCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));
		stopCal.setTime(formatter.parse(stopDate));
		createProject(projectName, startCal, stopCal,null);
	}

	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param projectManager The developer who is to manage the project
	 */
	public void createProject(String projectName, Developer projectManager) {
		createProject(projectName, null, null, projectManager);
	}
	
	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 */
	public void createProject(String projectName) {
		createProject(projectName, null, null, null);
	}
	
	/**
	 * Retrieves the list of developers
	 * @return developerList
	 */
	public List<Developer> getDevelopers() {
		return this.developerList;
	}

	/**
	 * Checks whether the scheduling app contains any project with the given name.
	 * @param name The name of the project being checked against
	 * @return True if a project with exactly name {@code name} exists, otherwise false
	 */
	boolean hasProjectNamed(String name) {
		return projectList.stream().anyMatch(p -> p.getName().equals(name));
	}
	
	/**
	 * Checks whether the scheduling app contains any developer with the given initials.
	 * @param initials The initials of a developer being checked against
	 * @return True if a developer with the current initials exists, otherwise false
	 */
	public boolean hasDeveloperWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}
	

	/**
	 * Checks whether the scheduling app contains any project with the given project number.
	 * @param projectNumber The project number of the project being checked against
	 * @return True if a project with project number {@code projectNumber} exists, otherwise false
	 */
	public boolean hasProjectWithNumber(String projectNumber) {
		return projectList.stream().anyMatch(p -> p.getProjectNumber().equals(projectNumber));
	}


	/**
	 * Returns a handle to the project with a given name 
	 * @param name The name of the project to be found
	 * @return A handle to the project if it exists, otherwise throws an exception
	 * @throws NoSuchElementException if the project was not found
	 */
	Project getProjectByName(String name) throws NoSuchElementException {
		List<Project> matches =  projectList.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		}
		throw new NoSuchElementException("This project does not exist");
	}

	/**
	 * Returns a handle to the developer with the given set of initials
	 * @param initials The initials of the developer to be found
	 * @return A handle to the developer if any developer with those initials exists, null otherwisee
	 */
	Developer getDeveloperByInitials(String initials) {
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
	void addDeveloper(Developer developer) {
		developerList.add(developer);
	}

	/**
	 * Checks whether a developer with the given set of initials exists.
	 * @param initials The initials to be checked against
	 * @return True if a developer exists, false otherwise
	 */
	boolean hasDevWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

	/**
	 * Returns a handle to the developer/user currently logged into the system
	 */
	Developer getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Returns a list of all available developers (defined as having fewer activities than their own maximum number)
	 * @return A list of all developers d, where d.activeActivities < d.maxActivities
	 */
	Developer[] getAvailableDevelopers() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * ------ CONTROLLER INTERFACE METHODS START HERE ------
	 */

	
	@Override
	public void setActiveProject(ProjectInfo projInfo) throws NoSuchElementException {
		activeProject = getProjectByName(projInfo.getName());
		support.firePropertyChange("project", null, projInfo);
	}
	
	@Override
	public void setActiveActivity(ActivityInfo actInfo) throws NoSuchElementException {
		this.activeActivity = activeProject.getActivityByName(actInfo.getName());
		support.firePropertyChange("activity", null, actInfo);
	}
	
	@Override
	public boolean login(String initials) {
		Developer dev = getDeveloperByInitials(initials);
		if(dev == null) {
			return false;
		}
		this.setCurrentUser(dev);
		support.firePropertyChange("user", null, new DeveloperInfo(currentUser));
		return true;
	}
	
	@Override
	public void logout() {
		support.firePropertyChange("user", currentUser, null);
		this.setCurrentUser(null);
	}

	@Override
	public List<ActivityInfo> getActiveProjectActivities() {
		return ActivityInfo.list2dto(activeProject.getActivityList());
	}
	
	@Override
	public DeveloperInfo createDeveloper(String name, String initials) {
		Developer d = new Developer(initials, name);
		addDeveloper(d);
		return new DeveloperInfo(d);
	}


	
	@Override
	public ProjectInfo createProject(String name) {
		Project p = new Project(name);
		p.setApp(this);
		projectList.add(p);
		return new ProjectInfo(p);
	}
	
	@Override
	public void setProjectManager(ProjectInfo projInfo, DeveloperInfo devInfo) {
		Project p = getProjectByName(projInfo.getName());
		Developer d = getDeveloperByInitials(devInfo.getInitials());
		p.setProjectManager(d);
	}
	
	@Override 
	public ActivityInfo createActivity(ProjectInfo projInfo, String name) {
		Project p = getProjectByName(projInfo.getName());
		p.createActivity(name);
		Activity a = p.getActivityByName(name);
		return new ActivityInfo(a);
	}
	
	@Override
	public List<ProjectInfo> getAllProjects() {
		return ProjectInfo.list2dto(projectList);
	}
	
	@Override
	public void addObserver(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	@Override
	public void registerTimeOnActivity(Calendar date, double hours) {
		activeActivity.registerTime(currentUser, (int) hours, date);
		support.firePropertyChange("time", null, hours);
	}
	
	@Override
	public void editTimeOnActivity(Calendar date, double hours) {
		activeActivity.editTime(currentUser, hours, date);
	}


	/**
	 * Returns the amount of projects created a given year
	 * @param year The year to get amount of created projects for
	 * @return The amount of projects created that year
	 */
	public int getAmountOfProjectsCreatedYear(int year) {
		List<Project> matches =  projectList.stream().filter(p -> p.getCreationDate().get(1) == year).collect(Collectors.toList());
		return matches.size();
	}

	/**
	 * Returns a list of all available developers
	 * @return A list of all available developers
	 * @throws Exception If the current user is not a project manager
	 */
	public List<Developer> viewAvailableDevelopers() throws Exception {
		assert developerList != null && this.currentUser != null;
		
		if(!this.getCurrentUser().isProjectManager()) {
			throw new IllegalArgumentException("Only project managers can view available developers");
		}
		List<Developer> availableDevelopers = developerList.stream().filter(d -> d.isAvailable()).collect(Collectors.toList());
		
		assert developerList.stream().filter(dev -> dev.isAvailable()).allMatch(dev -> availableDevelopers.contains(dev));
		assert availableDevelopers.stream().allMatch(dev -> developerList.contains(dev) && dev.isAvailable());
		
		return availableDevelopers;
	}
}
