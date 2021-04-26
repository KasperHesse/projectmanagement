package schedulingapp;

import java.util.ArrayList;
import java.util.List;

public class Developer {
	private List<Activity> activityList;
	private List<Project> projectList;
	private String name;
	private String initials;
	
	/**
	 * Creates a new developer with the given data. And adds the person to the activitys "holiday" and "course" under the project "misc"
	 * @param initials The initials of the developer
	 * @param name The full name of the developer
	 * @note The initials *must* be a string exactly 4 characters long.
	 */
	public Developer(String initials, String name) {
		this.name = name;
		this.initials = initials;

		this.activityList = new ArrayList<Activity>(); 
		this.projectList = new ArrayList<Project>();

	}
	
	/**
	 * Checks whether the developer is project manager on any project
	 * @return true, if developer is project manager of a project
	 */
	public boolean isProjectManager() {
		return projectList.stream().anyMatch(p -> p.isProjectManager(this) == true);
	}
	
	/**
	 * Checks if the developer is available, defined as working on less than 10 projects
	 * @return true, if developer is available
	 */
	public boolean isAvailable() {

		if (activityList.size() <= 10) {

			return true;
		}
		return false;

	}

	/**
	 * @return initials of the developer
	 */
	public String getInitials() {
		return this.initials;
	}

	/**
	 * Compares this Developer to another Developer, and returns true if they represent the same object /
	 * if they have the same set of initials
	 * @return true if these Developers are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else {
			Developer otherDev = (Developer) obj;
			return otherDev.getInitials().equals(this.initials);
		}
	}

	
	public List<Activity> getActivtyList() {
		return activityList; 
	}
	
	public void addDeveloperToActivity(Activity activity) {
		activityList.add(activity);
	}


	/**
	 * Adds a project to the developers list of projects
	 * @param project The project to be added to the project list
	 */
	public void addProject(Project project) {
		projectList.add(project);
		

	}
}
