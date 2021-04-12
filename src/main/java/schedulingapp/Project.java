package schedulingapp;

import java.util.Calendar;
import java.util.List;

public class Project {
	private Developer projectManager;
	private Calendar startDate;
	private Calendar stopDate;
	private String projectNumber;
	private String projectName;
	private List<Activity> activityList;
	
	public Project(String projectName, Calendar startDate, Calendar stopDate, Developer projectManager) {
		//Auto-generate projectNumber
		this.projectName = projectName;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.projectManager = projectManager;
	}
	
	/**
	 * Removes an activtiy from the project
	 * @param activity the activity to be removed
	 */
	public void removeActivity(Activity activity) {
		
	}
	
	public void createActivity() {
		
	}
	
	public TimeSheet getTimeReport() {
		return null;
	}
	
	public List<Developer> getAssignedDevelopers(){
		return null;
	}
	
	public boolean isProjectManager(Developer dev) {
		return true;
	}
	
	public boolean hasActivityNamed(String name) {
		return true;
	}
}
