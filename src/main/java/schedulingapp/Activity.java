package schedulingapp;

import java.util.Calendar;
import java.util.List;

public class Activity {
	private Calendar startDate;
	private Calendar stopDate;
	private int hoursBudgetted;
	private static List<Developer> developerList;
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
	
<<<<<<< Updated upstream
=======
	/**
	 * Checks whether a developer with the given initials is already working on this project
	 * @param initials The initials to check against
	 * @return True if a developer has this set of initials, false otherwise
	 */
	public static boolean hasDeveloperWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

>>>>>>> Stashed changes
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
	
}
