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
			developerList.add(schedulingApp.getCurrentUser());
			schedulingApp.getCurrentUser().addProject(this);			
		}

	}
	
	/**
	 * Creates a new Project with a given name. startDate, stopDate and projectManager are set to null
	 * @param projectName
	 * @param schedulingApp
	 */
	private String generateProjectNumber() {
		// TODO Auto-generated method stub
		return "123456";
	public Project(String projectName, SchedulingApp schedulingApp) {
		this(projectName, null, null, null, schedulingApp);
	}
	
	/**
	 * Creates a new Project with a given name and projectManager. startDate and stopDate are set to null
	 * @param projectName
	 * @param projectManager
	 * @param schedulingApp
	 */
	public Project(String projectName, Developer projectManager, SchedulingApp schedulingApp) {
		this(projectName, null, null, projectManager, schedulingApp);
	}
		
	
	public void addDeveloper(Developer dev) {
		if(doesDeveloperExistInProject(dev)) {
			throw new IllegalArgumentException("This developer is already a part of this project");
		} else if(!this.isProjectManager(this.getCurrentUser())) {
			throw new IllegalArgumentException("Developers cannot add other developers to projects");
		}
		this.developerList.add(dev);
		dev.addProject(this);
	}

	
	/**
	 * Generates a project number for a newly created project. The project number must be unique
	 * @return projectNumber
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
	 * @param activity the activity to be removed

	 */
	public void removeActivity(String name) {
		if (hasActivityNamed(name)) {
		activityList.remove(getActivityByName(name));
		} else {
			throw new IllegalArgumentException("No activity with this name exists in this project");
		}
	}
	
	/**
	 * Returns the TimeSheet for this project
	 */
	public TimeSheet getTimeReport() {
		return null;
	}
	
	
	//Does this work as intended? Same as above?
	public List<Developer> getDevelopers() {
		List<Developer> allDevs = developerList;
		
		for (Activity activity : activityList) {
			allDevs.addAll(activity.getDevelopers());
		}
		
		return allDevs.stream().distinct().collect(Collectors.toList());
		
	}
	
	
	/**
	 * Checks if the current developer exists in the project
	 * @param dev The developer in question
	 * @return True if this developer is a part of the current project, false otherwise
	 */
	public boolean doesDeveloperExistInProject(Developer dev){
		return activityList.stream().anyMatch(a -> a.getDevelopers().contains(dev));
	}
	
	
	/**
	 * Checks whether the given developer is the project manager of this project
	 * @param dev The developer in question
	 * @return True if this developer is the project manager of this project, false otherwise
	 */
	public boolean isProjectManager(Developer dev) {
		return dev.equals(this.projectManager);
	}
	
	/**
	 * Checks whether this project has an activity with the given name (case sensitive)
	 * @param name The name of the activity being checked against
	 * @return True if such an activity exists, false otherwise
	 */
	public boolean hasActivityNamed(String name) {
		return activityList.stream().anyMatch(a -> a.getName().equals(name));
	}
	
	/**
	 * Checks whether this project has a project manager
	 * @return True/False Returns true if a project manager exists for the current project and returns false if no project manager exists for the current project.
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
	 */
	
	public Developer getProjectManager() {
		return this.projectManager;
	}

	/**
	 * Returns the name of this project
	 */
	public String getName() {
		return this.projectName;
	}

	/**
	 * Sets the project manager of the project to the developer passed as parameter
	 * @param dev The new project manager of the project
	 */
	public void setProjectManager(Developer dev) {
		this.projectManager = dev;
		dev.addProject(this);
	}
	

	/**
	 * Creates an activity with a given name, starting and end date under the current project
	 * @param name The name of the activity
	 * @param start The start date of the activity 
	 * @param end The end date of the activity
	 */
	public void createActivity(String name, Calendar startDate, Calendar stopDate) {
		if(this.hasActivityNamed(name)) {
			throw new IllegalArgumentException("Activities must have a unique name");
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
	 */
	public void createActivity(String name, String startDate, String stopDate) throws ParseException {
		//TODO Catch this exception
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
	 * Sets a link between this project and the scheduling app
	 * @param schedulingApp The scheduling application to which this project belongs
	 */
	public void setApp(SchedulingApp schedulingApp) {
		this.schedulingApp = schedulingApp;
	}
	
	/**
	 * Returns a handle to the current user of the system
	 */
	public Developer getCurrentUser() {
		return this.schedulingApp.getCurrentUser();
	}

	
	public SchedulingApp getSchedulingApp() {
		return schedulingApp;
	}



	/**
	 * Returns a *copy* of the start date for this activity. Modifications on that copy will not modify the start date of the activity
	 */
	public Calendar getStartDate() {
		return (Calendar) this.startDate.clone();
	}

		/**
	 * Returns a *copy* of the end date for this activity. Modifications on that copy will not modify the end date of the activity
	 */
	public Calendar getStopDate() {
		return (Calendar) this.stopDate.clone();
	}
	
	/**
	 * changing number of weeks to start date of the current project.
	 * 
	 * @param weeks number of weeks being changed
	 */
	public void changeStartDate(int weeks) {
		startDatePast = startDate;

		if (startDate != null) {
			startDate.add(Calendar.WEEK_OF_YEAR, weeks);

		} else {
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
	}
	
	/**
	 * changing number of weeks to stop date of the current project.
	 * @param weeks number of weeks being changed
	 */
	public void changeStopDate(int weeks) {
		stopDatePast = stopDate;
		if (this.stopDate != null) {
			this.stopDate.add(Calendar.WEEK_OF_YEAR, weeks);
			} else {
			throw new IllegalArgumentException("A date for a project must be given before adding.");
			}
			if (stopDate.after(startDate)) {
				stopDate = stopDatePast;
	
			throw new IllegalArgumentException("The startdate cannot be after the stopdate");

		}
			if (stopDate.before(creationDate)) {
				stopDate = stopDatePast;
			throw new IllegalArgumentException("The startdate cannot be before the creationdate");
		}
	}

	/**
	 * Removes an activity from the project
	 * @param activity the activity to be removed
	 */
	public void removeDeveloper(Developer dev) {
		if (developerList.contains(dev)) {
		developerList.remove(dev);
		} else {
			throw new IllegalArgumentException("No developer with this initials exists in this project");
		}
	}


	/**
	 * @return the date of creation for the project
	 */
	public Calendar getCreationDate() {
		return this.creationDate;
	}

	/**
	 * @return the project number of the project
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}

	public void setStartDate(String startDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startCal = Calendar.getInstance();
		
		startCal.setTime(formatter.parse(startDate));

		this.startDate = startCal;

	}
	
	public void setStopDate(String stopDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar stopCal = Calendar.getInstance();
		
		stopCal.setTime(formatter.parse(stopDate));

		this.stopDate = stopCal;
		
	}
	
	public void setCreationDate(String creationDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar creationCal = Calendar.getInstance();
		
		creationCal.setTime(formatter.parse(creationDate));

		this.creationDate = creationCal;
	}
	List<Activity> getActivityList() {
		return this.activityList;
		
	}


}
