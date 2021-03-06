package schedulingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Project {
	private Developer projectManager;
	private Calendar startDate;
	private Calendar stopDate;
	private Calendar startDatePast;
	private Calendar stopDatePast;
	private String projectNumber;
	private String projectName;
	private List<Activity> activityList;
	private SchedulingApp schedulingApp;
	private Calendar creationDate;
	private List<Developer> developerList;

	
	/**
	 * Creates a new Project with a given name, startDate, stopDate and projectManager
	 * @param projectName the name of the project
	 * @param startDate the start date of the project, as Calendar object
	 * @param stopDate the stop date of the project, as Calendar object
	 * @param projectManager the developer who is to be project manager of the project
	 * @param schedulingApp the SchedulingApp to manage this project in
	 * @author Emil Mortensen, s204483
	 */
	public Project(String projectName, Calendar startDate, Calendar stopDate, Developer projectManager, SchedulingApp schedulingApp) {
		this.projectName = projectName;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.projectManager = projectManager;
		this.schedulingApp = schedulingApp;
		this.creationDate = new GregorianCalendar();
		this.activityList = new ArrayList<Activity>();
		this.projectNumber = generateProjectNumber();
		developerList = new ArrayList<Developer>();
		
		if (projectManager != null) {
			developerList.add(projectManager);
			projectManager.addProject(this);			
		}
		if (schedulingApp.getCurrentUser() != null) {
			this.developerList.add(schedulingApp.getCurrentUser());
			schedulingApp.getCurrentUser().addProject(this);		
		}

	}
	
		
	/**
	 * Adds a developer to this project
	 * @param dev the developer that is added to the project
	 * @author Emil Mortensen, s204483
	 */
	public void addDeveloper(Developer dev) {
		if(developerList.contains(dev)) {
			throw new IllegalArgumentException("This developer is already a part of this project");
		} else if(this.projectManager != null && !this.isProjectManager(this.getCurrentUser())) {
			throw new IllegalArgumentException("Developers cannot add other developers to projects");
		}
		this.developerList.add(dev);

		if(!dev.getProjects().contains(this)) { //Only add if they're not already assisting on this project
			dev.addProject(this);
		} else {
			dev.promoteFromAssistant(this);
		}
	}

	/**
	 * Generates a project number for a newly created project. The project number must be unique
	 * @return projectNumber a unique auto-generated number, based on the date and amount of current projects
	 * @author Emil Mortensen, s204483
	 */
	private String generateProjectNumber() {
		int year = creationDate.get(Calendar.YEAR);
		String serialNumber = "" + schedulingApp.getAmountOfProjectsCreatedYear(year);
		int stop = serialNumber.length();
		for (int i = 0; i < (4-stop); i++) {
			serialNumber = 0 + serialNumber;
		}
		return year%100 + serialNumber;
	}

	/**
	 * Removes an activity from the project
	 * @param name the name of the activity to be removed
	 * @author Jonathan Michelsen, s204437
	 */
	public void removeActivity(String name) {
		if (hasActivityNamed(name)) {
		activityList.remove(getActivityByName(name));
		} else {
			throw new IllegalArgumentException("No activity with this name exists in this project");
		}
	}	
	
	/**
	 * Returns a distinct list of all developers on this project. 
	 * The list is a concatenation of the developers on all the activites of the project and the developers only assigned to the project
	 * @return a list of all developers associated with the project
	 * @author Emil Mortensen, s204483
	 */
	public List<Developer> getDevelopers() {
		List<Developer> allDevs = developerList;
		return allDevs.stream().distinct().collect(Collectors.toList());
		
	}
	
	
	/**
	 * Checks if the current developer exists in the project
	 * @param dev The developer in question
	 * @return True if this developer is a part of the current project, false otherwise
	 * @author Jonathan Michelsen, s204437
	 */
	public boolean developerExistsInProject(Developer dev){
		return developerList.stream().anyMatch(d -> d.equals(dev));
	}
	
	/**
	 * Checks whether the given developer is the project manager of this project
	 * @param dev The developer in question
	 * @return True if this developer is the project manager of this project, false otherwise
	 * @author Kasper Hesse, s183735
	 */
	public boolean isProjectManager(Developer dev) {
		return dev.equals(this.projectManager);
	}
	
	/**
	 * Checks whether this project has an activity with the given name (case sensitive)
	 * @param name The name of the activity being checked against
	 * @return True if such an activity exists, false otherwise
	 * @author Kasper Hesse, s183735
	 */
	public boolean hasActivityNamed(String name) {
		return activityList.stream().anyMatch(a -> a.getName().equals(name));
	}
	
	/**
	 * Checks whether this project has a project manager
	 * @return True/False Returns true if a project manager exists for the current project and returns false if no project manager exists for the current project.
	 * @author Jonathan Michelsen, s204437
	 */
	public boolean hasProjectManager() {
		if (projectManager != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the ProjectManager of the current project.
	 * @author Peter Ejlev, s183718
	 */
	public Developer getProjectManager() {
		return this.projectManager;
	}

	/**
	 * Returns the name of this project
	 * @author Peter Ejlev, s183718
	 */
	public String getName() {
		return this.projectName;
	}

	/**
	 * Sets the project manager of the project to the developer passed as parameter
	 * @param dev The new project manager of the project
	 * @author Jonathan Michelsen, s204437
	 */
	public void setProjectManager(Developer dev) {
		if(schedulingApp.getCurrentUser() != this.projectManager && this.projectManager != null) {
			throw new IllegalArgumentException("Only the current PM can change the project manager");
		}
		try {
			this.addDeveloper(dev);
			this.projectManager = dev;
		} catch (IllegalArgumentException e) {
			if(developerList.contains(dev)) { //Error may be thrown if new PM already exists under this project
				this.projectManager = dev;
			}
		}
	}
	

	/**
	 * Creates an activity with a given name, starting and end date under the current project
	 * @param name The name of the activity
	 * @param start The start date of the activity 
	 * @param end The end date of the activity
	 * @author Kasper Hesse, s183735
	 */
	public void createActivity(String name, Calendar startDate, Calendar stopDate) {
		if(this.hasActivityNamed(name)) {
			throw new IllegalArgumentException("An activity with this name already exists. Activities must have a unique name");
		} else if(this.projectManager == null) {
			throw new IllegalArgumentException("Developers cannot create new activities");
		} else if(!schedulingApp.getCurrentUser().equals(this.projectManager)) {
			throw new IllegalArgumentException("Developers cannot create new activities");
		}
		activityList.add(new Activity(name, 0, startDate, stopDate, this));
	}
	
	/**
	 * Creates an activity under the current project with a given name
	 * @param name The name of the activity to be created
	 * @throws IllegalArgumentException if an activity with the same name already exists
	 * @author Kasper Hesse, s183735
	 */
	public void createActivity(String name) throws IllegalArgumentException {
		Calendar cal = null;
		createActivity(name, cal, cal); //Must use cal as argument to ensure we call method with signature (string, calendar, calendar)
	}

	/**
	 * Creates an activity with a given name, start date and end date
	 * @param name The name of the activity
	 * @param startDate The start date of the activity formatted as "yyyy-MM-dd" (eg. 2021-02-28)
	 * @param stopDate The end date of the activity formatted as "yyyy-MM-dd" (eg. 2021-02-28)
	 * @throws ParseException If any of the provided dates could not be parsed properly
	 * @author Kasper Hesse, s183735
	 */
	public void createActivity(String name, String startDate, String stopDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar startCal = Calendar.getInstance();
		Calendar stopCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));
		stopCal.setTime(formatter.parse(stopDate));
		createActivity(name, startCal, stopCal);
	}

	/**
	 * Returns a handle to an activity in this project with a given name. If no activity is found, throws an exception
	 * @param name The name of the activity to be retrieved
	 * @return A handle to the activity with the given name
	 * @throws NoSuchElementException if no activity with that name was found
	 * @author Kasper Hesse, s183735
	 */
	public Activity getActivityByName(String name) {
		List<Activity> matches = activityList.stream().filter(a -> a.getName().equals(name)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		} else {
			throw new NoSuchElementException("No activity with that name exists");
		}
	}
	
	/**
	 * Returns a handle to the current user of the system
	 * @author Emil Mortensen, s204483
	 */
	public Developer getCurrentUser() {
		return this.schedulingApp.getCurrentUser();
	}

	/**
	 * 
	 * @author Peter Ejlev, s183718
	 */
	public SchedulingApp getSchedulingApp() {
		return schedulingApp;
	}


	/**
	 * Returns a *copy* of the start date for this activity. Modifications on that copy will not modify the start date of the activity.
	 * If no start date is set, returns null
	 * @author Kasper Hesse, s183735
	 */
	public Calendar getStartDate() {
		if(this.startDate == null) {
			return null;
		}
		return (Calendar) this.startDate.clone();
	}

	/**
	 * Returns a *copy* of the end date for this activity. Modifications on that copy will not modify the end date of the activity.
	 * If no start date is set, returns null
	 * @author Kasper Hesse, s183735
	 */
	public Calendar getStopDate() {
		if(this.stopDate == null) {
			return null;
		}
		return (Calendar) this.stopDate.clone();
	}
	
	/**
	 * changing number of weeks to start date of the current project.
	 * 
	 * @param weeks number of weeks being changed
	 * @author Jonathan Michelsen, s204437
	 */
	public void changeStartDate(int weeks) {
		startDatePast = startDate;
				
		assert weeks == (int)weeks : "PreCondition changeStartDate";
		
		if (startDate != null) {
			startDate.add(Calendar.WEEK_OF_YEAR, weeks);
			
		} else  {
			throw new IllegalArgumentException("A date for a project must be given before changing");
		}
			
		if (startDate.after(stopDate)) {
			
			startDate = startDatePast;
			throw new IllegalArgumentException("The startdate cannot be after the stopdate");
		}
		
		if (startDate.before(creationDate)) {

			startDate = startDatePast;
			throw new IllegalArgumentException("The startdate cannot be before the creationdate");
		}
		
		assert startDate.equals(getStartDate()) : "PostCondition changeStartDate";
	}
	
	/**
	 * changing number of weeks to stop date of the current project.
	 * @param weeks number of weeks being changed
	 * @author Jonathan Michelsen, s204437
	 */
	public void changeStopDate(int weeks) {
		stopDatePast = stopDate;
		
		if (stopDate != null) {
			stopDate.add(Calendar.WEEK_OF_YEAR, weeks);
			
		} else  {
			throw new IllegalArgumentException("A date for a project must be given before changing");
		}
			
		if (stopDate.before(startDate)) {
			
			stopDate = stopDatePast;
			throw new IllegalArgumentException("The startdate cannot be after the stopdate");
		}
		
		if (stopDate.before(creationDate)) {

			stopDate = stopDatePast;
			throw new IllegalArgumentException("The stopdate cannot be before the creationdate");
		}
	}

	/**
	 * Removes a developer from the project
	 * @param dev the developer to be removed
	 * @author Kasper Hesse, s183735
	 */
	public void removeDeveloper(Developer dev) {
		if(dev == this.projectManager) {
			throw new IllegalArgumentException("Project managers cannot remove themselves from a project. Promote a new PM first");
		} else if(developerList.contains(dev)) {
			developerList.remove(dev);
			dev.removeProject(this);
		} else {
			throw new IllegalArgumentException("No developer with these initials exists in this project");
		}
	}


	/**
	 * @return the date of creation for the project
	 * @author Jonathan Michelsen, s204437
	 */
	public Calendar getCreationDate() {
		return this.creationDate;
	}

	/**
	 * @return the project number of the project
	 * @author Emil Mortensen, s204483
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}

	/**
	 * Set the start date of a project
	 * @param startDate the new start date of the project
	 * @throws ParseException
	 * @author Jonathan Michelsen, s204437
	 */
	public void setStartDate(String startDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startCal = Calendar.getInstance();
		
		startCal.setTime(formatter.parse(startDate));

		this.startDate = startCal;

	}
	
	/**
	 * Set the stop date of the project
	 * @param stopDate the new stop date
	 * @throws ParseException
	 * @author Jonathan Michelsen, s204437
	 */
	public void setStopDate(String stopDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar stopCal = Calendar.getInstance();
		
		stopCal.setTime(formatter.parse(stopDate));

		this.stopDate = stopCal;
		
	}
	
	/**
	 * Sets the creation date of the project. Only used for creating specific tests
	 * @param creationDate the new creation date
	 * @throws ParseException
	 * @author Jonathan Michelsen, s204437
	 */
	public void setCreationDate(String creationDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar creationCal = Calendar.getInstance();
		
		creationCal.setTime(formatter.parse(creationDate));

		this.creationDate = creationCal;
	}
	
	/**
	 * 
	 * @author Peter Ejlev, s183718
	 */
	List<Activity> getActivityList() {
		return this.activityList;
	}
	
	/**
	 * Checks whether two projects are the same. This is true if they represent the same object in memory OR
	 * if they have the same project number
	 * @author Kasper Hesse, s183735
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		} else {
			Project otherProject = (Project) obj;
			return this.projectNumber.equals(otherProject.getProjectNumber());
		}
	}


}
