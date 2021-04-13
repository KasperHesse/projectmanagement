package schedulingapp;

import java.util.List;

public class Developer {
	private List<Activity> activityList;
	private List<Project> projectList;
	private String name;
	private String initials;
	
	/**
	 * Creates a new developer with the given data. 
	 * @param initials The initials of the developer
	 * @param name The full name of the developer
	 * @note The initials *must* be a string exactly 4 characters long.
	 */
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
}
