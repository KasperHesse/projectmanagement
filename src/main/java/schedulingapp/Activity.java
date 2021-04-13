package schedulingapp;

import java.util.Calendar;
import java.util.List;

public class Activity {
	private Calendar startDate;
	private Calendar stopDate;
	private int hoursBudgetted;
	private List<Developer> developerList;
	private List<Developer> assistingDeveloperList;
	private TimeSheet timeSheet;
	private String activityName;
	
	public Activity(String activityName, int hoursBudgetted, Calendar startDate, Calendar stopDate) {
		this.activityName = activityName;
		this.hoursBudgetted = hoursBudgetted;
		this.startDate = startDate;
		this.stopDate = stopDate;
	}
	
	public void addDeveloper(Developer dev) {
		
	}
	
	public void removeDeveloper(Developer dev) {
		
	}
	
	public boolean isDeveloper(Developer dev) {
		return true;
	}
	
	public void askForHelp(Developer helper) {
		
	}
	
	public void registerTime(Developer dev, int hours, Calendar date) {
		
	}
	
	public void editTime(Developer dev, int hours, Calendar date) {
		
	}
	
	public void viewTime() {
		
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
	
}
