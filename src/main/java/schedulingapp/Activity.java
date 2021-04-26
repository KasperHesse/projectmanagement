package schedulingapp;

import java.util.*;

public class Activity {
	private Calendar startDate;
	private Calendar stopDate;
	private int hoursBudgetted;
	private int hoursBudgettedPast;
	private List<Developer> developerList;
	private List<Developer> assistingDeveloperList;
	private TimeSheet timeSheet;
	private String activityName;
	private Project project;
	
	public Activity(String activityName, int hoursBudgetted, Calendar startDate, Calendar stopDate, Project project) {
		this.activityName = activityName;
		this.hoursBudgetted = hoursBudgetted;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.project = project;
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
	}
	
	/**
	 * Checks whether a developer with the given initials is already working on this project
	 * @param initials The initials to check against
	 * @return True if a developer has this set of initials, false otherwise
	 */
	private boolean hasDeveloperWithInitials(String initials) {
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

	/**
	 * Returns a list of developers working on this activity. Any modifications to that list will not affect the activity.
	 * @return
	 */
	public List<Developer> getDevelopers() {
		return List.copyOf(this.developerList);
	}
	
	/**
	 * Adds number of weeks to the start date for the current activity
	 * @param weeks The amount of weeks
	 */
	
	public void addWeeksToStartDate(Integer weeks) {
		if (startDate != null) {
			this.startDate.add(Calendar.WEEK_OF_YEAR, weeks);
			} else {
			throw new IllegalArgumentException("A date for an activity must be given before adding.");
			}
	}
	
	/**
	 * Checks whether the start date has been changed
	 * @return Returns true if start date has been changed and false otherwise
	 */
	public boolean hasStartDateChanged() {
		if (startDate != null) {
			return true;
			} else {
			return false;
			}
	}

	/**
	 * Adds number of weeks to the start date for the current activity
	 * @param weeks The amount of weeks
	 */
	public void addWeeksToStopDate(Integer weeks) {
		if (stopDate != null) {
			this.stopDate.add(Calendar.WEEK_OF_YEAR, weeks);
			} else {
			throw new IllegalArgumentException("A date for an activity must be given before adding.");
			}
	}
	
	/**
	 * Checks whether the stop date has been changed
	 * @return Returns true if start date has been changed and false otherwise
	 */
	public boolean hasStopDateChanged() {
		if (stopDate != null) {
			return true;
			} else {
			return false;
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
	
	
}
