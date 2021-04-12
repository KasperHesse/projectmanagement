package schedulingapp;

import java.util.List;

public class Developer {
	private List<Activity> activityList;
	private List<Project> projectList;
	private String name;
	private String initials;
	
	//Initials consists of up to 4 letters
	public Developer(String initials, String name) {
		this.name = name;
		this.initials = initials;
	}
	
	public boolean isProjectManager() {
		return true;
	}
	
	public boolean isAvailable() {
		return true;
	}
}
