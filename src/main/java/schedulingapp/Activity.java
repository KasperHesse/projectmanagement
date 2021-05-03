package schedulingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Activity {
	private Calendar startDate;
	private Calendar stopDate;
	private int hoursBudgetted;
	private int hoursBudgettedPast;
	private List<Developer> developerList;
	private List<Developer> assistingDeveloperList;
	private TimeSheet timeSheet = new TimeSheet();
	private String activityName;
	private Project project;

	private Calendar creationDate;
	private Calendar startDatePast;
	private Calendar stopDatePast;


	
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
	
	public void addDeveloper(Developer dev) {
		if(this.hasDeveloperWithInitials(dev.getInitials())) {
			throw new IllegalArgumentException("This developer is already working on this activity");
		} else if(!this.project.isProjectManager(this.project.getCurrentUser())) {
			throw new IllegalArgumentException("Developers cannot add other developers to activities");
		}
		this.developerList.add(dev);
		dev.addProject(project);
		dev.addDeveloperToActivity(this);
		if(!project.doesDeveloperExistInProject(dev)) {
			project.addDeveloper(dev);
		}
	}
	
	/**
	 * Checks whether a developer with the given initials is already working on this project
	 * @param initials The initials to check against
	 * @return True if a developer has this set of initials, false otherwise
	 */
	public boolean hasDeveloperWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

	public void removeDeveloper(Developer dev) {
		if (developerList.contains(dev)) {
		developerList.remove(dev);
		} else {
			throw new IllegalArgumentException("No developer with this name exists in this project");
		}
	}
	
	public boolean isDeveloper(Developer dev) {
		return developerList.contains(dev);
	}
	
	public boolean isAssistingDeveloper(Developer dev) {
		return assistingDeveloperList.contains(dev);
	}
	
	/**
	 * Adds the given helper the given activitys assistingDeveloperlist if allowed. 
	 * @param Who youd like to ask for help
	 * @param On what activity you need helop for
	 */
	public void askForHelp(Developer helper, Activity activity) {
		if(!helper.isAvailable()) {
			throw new IllegalArgumentException("The requested developer is associated with max amount of activities");
		}
		
		if(isDeveloper(helper)) {
			throw new IllegalArgumentException("The requested developer is already associated with the activity");
		}
		
		if(!getProject().getSchedulingApp().getDevelopers().contains(helper)){
			throw new IllegalArgumentException("The requested developer is not in the system");
		}
		
		if(helper.isAvailable()) {
		assistingDeveloperList.add(helper);
		helper.addDeveloperToActivity(activity);
		}
	}
	
	/**
	 * Registers time on the given activity if allowed
	 * @param The developerregistering time
	 * @param How many hours to register
	 * @param On what date he wants register said hours
	 */
	public void registerTime(Developer dev, double hours, Calendar date) {
		//precondition
		assert dev != null && date != null && hours > 0;
		
		if(startDate != null && stopDate != null) {                                                             //1
			
			if(startDate.before(date) || stopDate.after(date)) {
				throw new IllegalArgumentException("You cannot register time outside the active status dates"); //2
			}
		}
		
		if(!isDeveloper(dev)) {                                                                                 //3
			throw new IllegalArgumentException("You are not associated with chosen activity");
		}
		
		timeSheet.registerTime(dev, hours, date);
		
		//postcondition
		assert viewTime(date, dev) == hours;
	}
	
	public void editTime(Developer dev, double hours, Calendar date) {
		
		assert dev != null && date != null && hours > 0;
		
		if(dev != this.getProject().getSchedulingApp().getCurrentUser()) {               																	//1                                                                  
			throw new IllegalArgumentException("You can't edit other developers registered time");
		}
		
		if(startDate != null && stopDate != null) {                                                             					//2
			
			if(startDate.before(date) || stopDate.after(date)) {
				throw new IllegalArgumentException("You cannot register time outside the active status dates"); 
			}
		}
		
		if (!isDeveloper(dev)) { 																									//3
			throw new IllegalArgumentException("You are not associated with chosen activity");
		}
		
		timeSheet.editTime(dev, date, hours);
	}
	
	/**
	 * Gets a Developers timeusage on a gsniiven date 
	 * @param what date you want to check
	 * @param what developer you want to check
	 * @return
	 */
	public int viewTime(Calendar date, Developer dev) {
		if(!isDeveloper(dev)) {                                                                                 
			throw new IllegalArgumentException("You can't view other developers registered time");
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
		return (Calendar) this.startDate.clone();
	}

		/**
	 * Returns a *copy* of the end date for this activity. Modifications on that copy will not modify the end date of the activity
	 */
	public Calendar getStopDate() {
		return (Calendar) this.stopDate.clone();
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public void setStopDate(Calendar stopDate) {
		this.stopDate = stopDate;
	}

	/**
	 * Returns a list of developers working on this activity. Any modifications to that list will not affect the activity.
	 * @return
	 */
	public List<Developer> getDevelopers() {
		return List.copyOf(this.developerList);
	}

	public void registerTime(Developer currentUser, Double hours, String date) {
		// TODO Auto-generated method stub
		
	}
	
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
			assert startDate != null : "PreCondition changeStartDate";
			startDate.add(Calendar.WEEK_OF_YEAR, weeks);

		} else {
			throw new IllegalArgumentException("A date for a project must be given before changing");
		}
		if (startDate.after(stopDate)) {
			assert startDate.after(stopDate) : "PostCondition changeStartDate";
			startDate = startDatePast;
			throw new IllegalArgumentException("The startdate cannot be after the stopdate");

		}
		if (startDate.before(creationDate)) {
			assert startDate.before(creationDate) : "PostCondition changeStartDate";
			startDate = startDatePast;
			throw new IllegalArgumentException("The startdate cannot be before the creationdate");
		}
	}
	
	/**
	 * Adds number of weeks to the start date for the current activity
	 * @param weeks The amount of weeks
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
	 * Adds number hours to the hours budgeted for the activity
	 * @param hours the amount of hours
	 */
	public void addHours(Integer hours) {
		this.hoursBudgettedPast = this.hoursBudgetted;
		this.hoursBudgetted += hours;
		
	}
	
	/**
	 * Checks whether the budgeted hours for the activity has been changed
	 * @param hours the amount of hours
	 * @return Returns true if budgeted hours has been changed false otherwise
	 */
	public boolean hoursHasChanged(Integer hours) {
		if(this.hoursBudgettedPast == this.hoursBudgetted-hours) {
			return true;
		} else {
			return false;
		}
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
}
