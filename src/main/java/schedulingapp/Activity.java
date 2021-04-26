package schedulingapp;

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
	//private SchedulingApp schedulingApp;
	
	public Activity(String activityName, int hoursBudgetted, Calendar startDate, Calendar stopDate, Project project) {
		this.activityName = activityName;
		this.hoursBudgetted = hoursBudgetted;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.project = project;
		this.developerList = new ArrayList<Developer>();
		this.assistingDeveloperList = new ArrayList<Developer>();
		//this.schedulingApp = schedulingApp;
	}
	
	public void addDeveloper(Developer dev) {
		if(this.hasDeveloperWithInitials(dev.getInitials())) {
			throw new IllegalArgumentException("This developer is already working on this activity");
		} else if(!this.project.isProjectManager(this.project.getCurrentUser())) {
			throw new IllegalArgumentException("Developers cannot add other developers to activities");
		}
		this.developerList.add(dev);
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
	public void registerTime(Developer dev, int hours, Calendar date) {
		if(!(startDate == null && stopDate == null)) {
			
			if(startDate.after(date) || stopDate.before(date)) {
				throw new IllegalArgumentException("You cannot register time outside the active status dates");
			}
		}
		
		if(!isDeveloper(dev)) {
			throw new IllegalArgumentException("You are not associated with chosen activity");
		}
		
		timeSheet.registerTime(dev, hours, date);
	}
	
	public void editTime(Developer dev, int hours, Calendar date) {
		
	}
	
	/**
	 * Gets a Developers timeusage on a given date 
	 * @param what date you want to check
	 * @param what developer you want to check
	 * @return
	 */
	public int viewTime(Calendar date, Developer dev) {
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

	/**
	 * Returns a list of developers working on this activity. Any modifications to that list will not affect the activity.
	 * @return
	 */
	public List<Developer> getDevelopers() {
		return List.copyOf(this.developerList);
	}
	
	public Project getProject() {
		return project;
	}
	
}
