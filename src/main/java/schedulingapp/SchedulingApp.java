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

	/**
	 * Constructor of SchedulingApp. Initializes developerList and projectList
	 */
	public SchedulingApp() {
		this.developerList = new ArrayList<Developer>();
		this.projectList = new ArrayList<Project>();
		this.activeProject = null;
	}
	
	/**
	 * Sets the current user of the schedulingApp
	 * @param currentUser The user currently logged in to the application
	 */
	void setCurrentUser(Developer currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param startDate The given date the project is expected to begin, as Calendar object
	 * @param stopDate The given date the project is expected to end, as Calendar object
	 * @param projectManager The developer who is to manage the project
	 */
	Project createProject(String projectName, Calendar startDate, Calendar stopDate, Developer projectManager) {
		Project p = new Project(projectName, startDate, stopDate, projectManager, this);
		projectList.add(p);
		return p;
	}
	
//	/**
//	 * Creates a project with the following information
//	 * @param projectName The name of the project
//	 * @param startDate The given date the project is expected to begin, as Calendar object
//	 * @param stopDate The given date the project is expected to end, as Calendar object
//	 */
//	void createProject(String projectName, Calendar startDate, Calendar stopDate) {
//		createProject(projectName, startDate, stopDate, null);
//	}
	
	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param startDate The given date the project is expected to begin, as a String formatted as "yyyy-MM-dd"
	 * @param stopDate The given date the project is expected to end, as a String formatted as "yyyy-MM-dd"
	 * @throws ParseException If the date-Strings are not correctly formatted
	 */
	void createProject(String projectName, String startDate, String stopDate) throws ParseException {
		//TODO Catch this exception
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar startCal = Calendar.getInstance();
		Calendar stopCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));
		stopCal.setTime(formatter.parse(stopDate));
		createProject(projectName, startCal, stopCal, null);
	}

	/**
	 * Creates a project with the following information
	 * @param projectName The name of the project
	 * @param projectManager The developer who is to manage the project
	 */
	void createProject(String projectName, Developer projectManager) {
		createProject(projectName, null, null, projectManager);
	}
	
	/**
	 * Retrieves the list of developers
	 * @return developerList
	 */
	List<Developer> getDevelopers() {
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
	 * Checks whether the scheduling app contains any project with the given project number.
	 * @param projectNumber The project number of the project being checked against
	 * @return True if a project with project number {@code projectNumber} exists, otherwise false
	 */
	boolean hasProjectWithNumber(String projectNumber) {
		return projectList.stream().anyMatch(p -> p.getProjectNumber().equals(projectNumber));
	}


	/**
	 * Returns a handle to the project with a given name. If more than one project exists with this name, returns the first project with that name
	 * @param name The name of the project to be found
	 * @return A handle to the project if it exists, otherwise throws an exception
	 * @throws NoSuchElementException if the project was not found
	 */
	Project getProjectByName(String name) throws NoSuchElementException {
		List<Project> matches =  projectList.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
		if(matches.size() >= 1) {
			return matches.get(0);
		}
		throw new NoSuchElementException("No project exists with the given name");
	}
	
	Project getProjectByNumber(String projectNumber) throws NoSuchElementException {
		List<Project> matches =  projectList.stream().filter(p -> p.getProjectNumber().equals(projectNumber)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		}
		throw new NoSuchElementException("No project exists with the given project number");
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
	 * Returns the amount of projects created a given year
	 * @param year The year to get amount of created projects for
	 * @return The amount of projects created that year
	 */
	int getAmountOfProjectsCreatedYear(int year) {
		List<Project> matches =  projectList.stream().filter(p -> p.getCreationDate().get(1) == year).collect(Collectors.toList());
		return matches.size();
	}

	/**
	 * Sorts all the available developers from the developer list
	 * @return A list of all available developers
	 * @throws Exception If the current user is not a project manager
	 */
	List<Developer> viewAvailableDevelopers() throws Exception {
		assert developerList != null && this.currentUser != null;
		
		if(!this.getCurrentUser().isProjectManager()) {
			throw new IllegalArgumentException("Only project managers can view available developers");
		}
		List<Developer> availableDevelopers = developerList.stream().filter(d -> d.isAvailable()).collect(Collectors.toList());
		
		assert developerList.stream().filter(dev -> dev.isAvailable()).allMatch(dev -> availableDevelopers.contains(dev));
		assert availableDevelopers.stream().allMatch(dev -> developerList.contains(dev) && dev.isAvailable());
		
		return availableDevelopers;
	}
	
	/*
	 * ------ CONTROLLER INTERFACE METHODS START HERE ------
	 */

	/**
	 * Returns a list of all projects which currently do not have a project manager associated
	 * @return
	 */
	private List<Project> getProjectsWithNoPM() {
		List<Project> list = projectList.stream().filter(p -> p.getProjectManager() == null).collect(Collectors.toList());
		return list;
	}
	
	/**
	 * Applies a pre-made config to the system, including a number of users, projects and activities
	 */
	public void presentationSetup() {
		createDeveloper("Kasper Hesse", "kahe");
		createDeveloper("Peter Ejlev", "pete");
		createDeveloper("Emil Pontoppidan", "emil");
		createDeveloper("Jonathan Michelsen", "jona");
		Developer kahe = getDeveloperByInitials("kahe");
		Developer pete = getDeveloperByInitials("pete");
		Developer emil = getDeveloperByInitials("emil");

		
		login("kahe");
		
		createProject("My first project");
		createProject("My second project");
		Project p1 = getProjectByName("My first project");
		Project p2 = getProjectByName("My second project");
		p1.setProjectManager(kahe);
		p1.addDeveloper(emil);
		p1.createActivity("My first activity");
		Activity a = p1.getActivityByName("My first activity");
		a.addDeveloper(kahe);
		a.askForHelp(pete);
		
		logout();
	}
	
	
	@Override
	public void setActiveProject(ProjectInfo projInfo) throws NoSuchElementException {
		activeProject = getProjectByNumber(projInfo.getProjectNumber());
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
//		return ActivityInfo.list2dto(activeProject.getActivityList());
		List<Activity> activities = new ArrayList<Activity>(activeProject.getActivityList());
		if(activeProject.getProjectManager() != null && this.currentUser.equals(activeProject.getProjectManager())) {
			return ActivityInfo.list2dto(activities);
		}
		activities = activities
				.stream()
				.filter(a -> a.hasDeveloperWithInitials(currentUser.getInitials()) 
						| a.hasAssistantDeveloperWithInitials(currentUser.getInitials()))
				.collect(Collectors.toList());
		return ActivityInfo.list2dto(activities);
	}
	
	@Override
	public DeveloperInfo createDeveloper(String name, String initials) {
		Developer d = new Developer(initials, name);
		addDeveloper(d);
		return new DeveloperInfo(d);
	}
	
	@Override
	public void createProject(String name) {
		createProject(name, null);
	}
	
//	@Override
//	public void setProjectManager(ProjectInfo projInfo, DeveloperInfo devInfo) {
//		Project p = getProjectByName(projInfo.getName());
//		Developer d = getDeveloperByInitials(devInfo.getInitials());
//		p.setProjectManager(d);
//	}
	
	@Override 
	public ActivityInfo createActivity(ProjectInfo projInfo, String name) {
		Project p = getProjectByNumber(projInfo.getProjectNumber());
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
		double oldHours = activeActivity.viewTime(date, currentUser);
		activeActivity.editTime(currentUser, hours, date);
		support.firePropertyChange("time", oldHours, hours + oldHours);
	}
	
	@Override
	public void assignDevToActivity(DeveloperInfo dev, ProjectInfo proj, ActivityInfo act) {
		Developer d = getDeveloperByInitials(dev.getInitials());
		Project p = getProjectByNumber(proj.getProjectNumber());
		Activity a = p.getActivityByName(act.getName());
		a.addDeveloper(d);
	}

	@Override
	public double getHoursOnActivity(Calendar date) {
		return activeActivity.viewTime(date, currentUser);
	}
	
	@Override
	public DeveloperInfo getDeveloperWithInitials(String initials) {
		Developer d = getDeveloperByInitials(initials);
		if(d == null) {
			return null;
		}
		return new DeveloperInfo(getDeveloperByInitials(initials));
	}

	@Override
	public void addAssistantDeveloper(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeActivity.askForHelp(dev);
		support.firePropertyChange("asstdev", null, devInfo);
	}

	@Override
	public List<DeveloperInfo> getAssistantDeveloperList() {
		return DeveloperInfo.list2dto(activeActivity.getAssistingDeveloperList());
	}
	
	@Override
	public ActivityInfo getActiveActivity() {
		return new ActivityInfo(activeActivity);
	}

	@Override
	public void removeAssistantDeveloper(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeActivity.removeAssistingDeveloper(dev);
		support.firePropertyChange("asstdev", devInfo, null);
	}

	@Override
	public boolean userIsProjectManager() {
		return currentUser == activeProject.getProjectManager();
	}
	
	@Override
	public List<ProjectInfo> getVisisbleProjects() {
		List<Project> projects = new ArrayList<Project>(currentUser.getProjects());
		projects.addAll(this.getProjectsWithNoPM());
		return ProjectInfo.list2dto(projects.stream().distinct().collect(Collectors.toList()));
	}
	
	@Override
	public int getActivityTimeBudget() {
		return activeActivity.getHoursBudgeted();
	}
	
	@Override
	public void setActivityTimeBudget(int hours) {
		activeActivity.setHoursBudgeted(hours);
		support.firePropertyChange("budget", null, hours);
	}

	@Override
	public List<DeveloperInfo> getProjectDeveloperList() {
		return DeveloperInfo.list2dto(activeProject.getDevelopers());
	}

	@Override
	public void addDeveloperToProject(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeProject.addDeveloper(dev);
		support.firePropertyChange("devlist", null, devInfo);
	}

	@Override
	public void removeDeveloperFromProject(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeProject.removeDeveloper(dev);
		support.firePropertyChange("devlist", devInfo, null);
	}

	@Override
	public void setProjectManager(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		DeveloperInfo oldPM = new DeveloperInfo(activeProject.getProjectManager());
		activeProject.setProjectManager(dev);
		support.firePropertyChange("projman", oldPM, devInfo);
	}

	@Override
	public DeveloperInfo getProjectManager() {
		Developer pm = activeProject.getProjectManager();
		if(pm == null) {
			return null;
		}
		return new DeveloperInfo(pm);
	}

	@Override
	public List<DeveloperInfo> getActivityDeveloperList() {
		return DeveloperInfo.list2dto(activeActivity.getDevelopers());
	}

	@Override
	public void addDeveloperToActivity(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeActivity.addDeveloper(dev);
		support.firePropertyChange("devlist", null, devInfo);
		
	}

	@Override
	public void removeDeveloperFromActivity(DeveloperInfo devInfo) {
		Developer dev = getDeveloperByInitials(devInfo.getInitials());
		activeActivity.removeDeveloper(dev);
		support.firePropertyChange("devlist", devInfo, null);
	}
	
	@Override
	public List<DeveloperInfo> getAvailableDevelopers() {
		return DeveloperInfo.list2dto(this.developerList.stream().filter(d -> d.isAvailable()).collect(Collectors.toList()));
	}
	
	@Override
	public void setActivityStartDate(Calendar date) {
		activeActivity.setStartDate(date);
		support.firePropertyChange("startdate", null, date);
	}
	
	@Override
	public void setActivityStopDate(Calendar date) {
		activeActivity.setStopDate(date);
		support.firePropertyChange("stopdate", null, date);
		
	}

	@Override
	public void addNewProject(String name, Calendar startDate, Calendar stopDate, DeveloperInfo projMan) {
		Developer pm = null;
		if(projMan != null) {
			pm = getDeveloperByInitials(projMan.getInitials());
		}
		Project p = createProject(name, startDate, stopDate, pm);
		support.firePropertyChange("projlist", null, new ProjectInfo(p));
	}

	@Override
	public void addNewActivity(String name, Calendar startDate, Calendar stopDate, Integer hoursToBudget) {
		activeProject.createActivity(name, startDate, stopDate);
		Activity a = activeProject.getActivityByName(name);
		support.firePropertyChange("actlist", null, new ActivityInfo(a));
	}

	@Override
	public ProjectInfo getActiveProject() {
		return new ProjectInfo(this.activeProject);
	}



}
