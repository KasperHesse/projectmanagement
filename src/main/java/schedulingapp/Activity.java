package schedulingapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Activity {
	private Calendar startDate;
	private Calendar stopDate;
	private int hoursBudgetted;
	private List<Developer> developerList;
	private List<Developer> assistingDeveloperList;
	private TimeSheet timeSheet = new TimeSheet();
	private String activityName;
	private Project project;
	private Calendar creationDate;
	private Calendar startDatePast;
	private Calendar stopDatePast;
	
	public static String cal2string(Calendar date) {
		DateFormat formatter = new SimpleDateFormat("yyyy 'W'w");
		if(date == null) {
			return "No date set";
		}
		return formatter.format(date.getTime());
	}


	/**
	 * Creates an activity with following information
	 * @param activityName the name of the activity
	 * @param hoursBudgetted the number of hours budgeted for the activity
	 * @param startDate the start date of the activity, as a Calendar object
	 * @param stopDate the stop date of the activity, as a Calendar object
	 * @param project the associated project
	 */
	public Activity(String activityName, int hoursBudgetted, Calendar startDate, Calendar stopDate, Project project) {
		this.activityName = activityName;
		this.hoursBudgetted = hoursBudgetted;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.project = project;
		this.creationDate = new GregorianCalendar();
		this.developerList = new ArrayList<Developer>();
		this.assistingDeveloperList = new ArrayList<Developer>();
	}
	
	/**
	 * Adds a developer to the activity
	 * @param dev the developer to be added to the activity
	 */
	public void addDeveloper(Developer dev) {
		if(this.hasDeveloperWithInitials(dev.getInitials())) {
			throw new IllegalArgumentException("This developer is already working on this activity");
		} else if(!this.project.isProjectManager(this.project.getCurrentUser())) {
			throw new IllegalArgumentException("Developers cannot add other developers to activities");
		}
		this.developerList.add(dev);
		dev.addProject(project);
		dev.addActivity(this);
		if(!project.developerExistsInProject(dev)) {
			project.addDeveloper(dev);
		}
	}
	
	/**
	 * Checks whether a developer with the given initials is already working on this activity
	 * @param initials The initials to check against
	 * @return True if a developer has this set of initials, false otherwise
	 */
	public boolean hasDeveloperWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

	/**
	 * Removes a given developer from the activity
	 * @param dev the developer to be removed from the activity
	 */
	public void removeDeveloper(Developer dev) {
		if (developerList.contains(dev)) {
			developerList.remove(dev);
			dev.removeActivity(this);
		} else {
			throw new IllegalArgumentException("No developer with this name exists in this project");
		}
	}
	
	public void removeAssistingDeveloper(Developer dev) {
		if(assistingDeveloperList.contains(dev)) {
			assistingDeveloperList.remove(dev);
			dev.removeActivity(this);
			dev.removeProject(this.project);
		} else {
			throw new IllegalArgumentException("No assisting developer with this name exists in this project");
		}
	}
	
	/**
	 * Determines if the given developer is working on this activity
	 * @param dev the developer to investigate
	 * @return true if the developer is working on the activity
	 */
	public boolean isDeveloper(Developer dev) {
		return developerList.contains(dev);
	}
	
	/**
	 * Checks if the developer is Assisting developer on the given activity
	 * @param the developer in question
	 * @return Boolean, true if developer in question is assisting developer
	 */
	public boolean isAssistingDeveloper(Developer dev) {
		return assistingDeveloperList.contains(dev);
	}
	
	/**
	 * Adds the given helper the given activity's assistingDeveloperlist if allowed. 
	 * @param Who you'd like to ask for help
	 * @param On what activity you need help for
	 */
	public void askForHelp(Developer helper) {
		if(!helper.isAvailable()) {
			throw new IllegalArgumentException("The requested developer is associated with max amount of activities");
		}
		
		if(isDeveloper(helper) || isAssistingDeveloper(helper)) {
			throw new IllegalArgumentException("The requested developer is already associated with the activity");
		}
		
		if(!getProject().getSchedulingApp().getDevelopers().contains(helper)){
			throw new IllegalArgumentException("The requested developer is not in the system");
		}
		
		
		assistingDeveloperList.add(helper);
		helper.addActivity(this);
		helper.addProject(this.project);
	}
	
	/**
	 * Registers time on the given activity if allowed
	 * @param The developer registering time
	 * @param How many hours to register
	 * @param On what date he wants to register said hours
	 */
	public void registerTime(Developer dev, double hours, Calendar date) {	
		//precondition
		assert dev != null && date != null && hours > 0;
		
		if(startDate != null && stopDate != null) {                                                             //1
			
			if(date.before(startDate) || date.after(stopDate)) {												//2
				throw new IllegalArgumentException("You cannot register time outside the active status dates"); 
			}
		}
		
		if(!isDeveloper(dev) && !isAssistingDeveloper(dev)) {                                                   //3                              
			throw new IllegalArgumentException("You are not associated with chosen activity");					
		}
		
		timeSheet.registerTime(dev, hours, date);
		
		//postcondition
		assert viewTime(date, dev) == hours;
	}
	
	/**
	 * Edits registered time on the given activity if allowed
	 * @param The developer that wants to edit their registered time
	 * @param How many hours to add to the registered time
	 * @param On what date he wants to register said hours
	 */
	public void editTime(Developer dev, double hours, Calendar date) {
		assert dev != null && date != null;
		
		if(dev != this.getProject().getSchedulingApp().getCurrentUser()) {               																	//1                                                                  
			throw new IllegalArgumentException("You can't edit other developers registered time");
		}
		
		if(startDate != null && stopDate != null) {                                                             					//2
			if(startDate.before(date) || stopDate.after(date)) {
				throw new IllegalArgumentException("You cannot register time outside the active status dates"); 
			}
		}
		if (!isDeveloper(dev) && !isAssistingDeveloper(dev)) { 																									//3
			throw new IllegalArgumentException("You are not associated with chosen activity");
		}
		
		double preEditTime = viewTime(date, dev);
		
		timeSheet.editTime(dev, date, hours);
		
		assert viewTime(date, dev) == preEditTime + hours;
	}
	
	/**
	 * Gets a Developers time usage on a given date 
	 * @param which date you want to check
	 * @param what developer you want to check
	 * @return the registered amount of hours
	 */
	public double viewTime(Calendar date, Developer dev) {
		if(!isDeveloper(dev) && !isAssistingDeveloper(dev)) {                                                                                 
			throw new IllegalArgumentException("You cannot view time on an activity you are not active on");
		}
		return timeSheet.viewTime(date, dev);
	}

	/**
	 * Returns the name of this activity
	 */
	public String getName() {
		return this.activityName;
	}

	/**
	 * Returns a *copy* of the start date for this activity. Modifications on that copy will not modify the start date of the activity
	 */
	public Calendar getStartDate() {
		if(this.startDate == null) {
			return null;
		}
		return (Calendar) this.startDate.clone();
	}

		/**
	 * Returns a *copy* of the end date for this activity. Modifications on that copy will not modify the end date of the activity
	 */
	public Calendar getStopDate() {
		if(this.stopDate == null) {
			return null;
		}
		return (Calendar) this.stopDate.clone();
	}
	
	/**
	 * Sets the startdate of an activity
	 * @param The settet date
	 */
	public void setStartDate(Calendar startDate) {
		if(this.stopDate != null && startDate.after(this.stopDate)) {
			throw new IllegalArgumentException(String.format("Given start date (%s) must be before stop date (%s)", Activity.cal2string(startDate), Activity.cal2string(this.stopDate)));
		}
		this.startDate = startDate;
	}
	
	/**
	 * Sets the stopdate of an activity
	 * @param the settet date
	 */
	public void setStopDate(Calendar stopDate) {
		if(this.startDate != null && stopDate.before(this.startDate)) {
			throw new IllegalArgumentException(String.format("Given stop date (%s) must be after start date (%s)", Activity.cal2string(stopDate), Activity.cal2string(this.startDate)));
		}
		this.stopDate = stopDate;
	}

	/**
	 * Returns a list of developers working on this activity. Any modifications to that list will not affect the activity.
	 * @return
	 */
	public List<Developer> getDevelopers() {
		return List.copyOf(this.developerList);
	}

//	public void registerTime(Developer currentUser, Double hours, String date) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public Project getProject() {
		return project;
	}
	
	/**
	 * Adds number of weeks to the start date for the current activity
	 * @param weeks The amount of weeks
	 */
	public void changeStartDate(int weeks) {
		startDatePast = startDate;	
		
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
		
	}
	
	/*
	 * Adds number of weeks to the start date for the current activity
	 * @param weeks The amount of weeks
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
	 * Sets the start date of the activity
	 * @param startDate the new start date of the activity
	 * @throws ParseException
	 */
	public void setStartDate(String startDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startCal = Calendar.getInstance();
		
		startCal.setTime(formatter.parse(startDate));

		this.startDate = startCal;

	}
	
	/**
	 * Sets the stop date of the activity
	 * @param stopDate the new stop date of the activity
	 * @throws ParseException
	 */
	public void setStopDate(String stopDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar stopCal = Calendar.getInstance();
		
		stopCal.setTime(formatter.parse(stopDate));

		this.stopDate = stopCal;
		
	}

	public List<Developer> getAssistingDeveloperList() {
		return this.assistingDeveloperList;
	}
	
	public int getHoursBudgeted() {
		return this.hoursBudgetted;
	}

	public void setHoursBudgeted(int hoursBudgetted) {
		this.hoursBudgetted = hoursBudgetted;
		
	}
	
	/**
	 * Moves a developer from the assisting developer list to the ordinary developer list
	 * @param developer The developer to move from assistant to ordinary developer
	 */
	void migrateDeveloper(Developer developer) {
		this.assistingDeveloperList.remove(developer);
		if (!developerList.contains(developer)) {
			this.developerList.add(developer);	
		}
	}


	/**
	 * Verifies whether the activity has an assistant developer with the given initials working on the activity
	 * @param initials The initials to check against
	 * @return True if the developer with those initials is assisting on this activity, false otherwise
	 */
	public boolean hasAssistantDeveloperWithInitials(String initials) {
		return assistingDeveloperList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

}
