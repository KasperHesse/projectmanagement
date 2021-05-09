package schedulingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.ProjectInfo;

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

		this.activityList = new ArrayList<Activity>(); 
		this.projectList = new ArrayList<Project>();
	}
	
	/**
	 * Checks whether the developer is project manager on any project
	 * @return true, if developer is project manager of a project
	 */
	public boolean isProjectManager() {
		return projectList.stream().anyMatch(p -> p.isProjectManager(this));
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
	 * Compares this Developer to another Developer, and returns true if they represent the same object OR
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

//	/**
//	 * @return activityList the list of activities the developer is working on
//	 */
//	public List<Activity> getActivityList() {
//		return activityList; 
//	}
	
	/**
	 * Adds the developer to the given activity
	 * @param activity the activity to add the developer to
	 */
	public void addActivity(Activity activity) {
		if(!activityList.contains(activity)) {
			activityList.add(activity);
		}
	}


	/**
	 * Adds a project to the developers list of projects
	 * @param project The project to be added to the project list
	 */
	public void addProject(Project project) {
		if(!projectList.contains(project)) {
			projectList.add(project);
		}
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Removes an activity from the developers list of activities
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		this.activityList.remove(activity);
	}

	/**
	 * Returns a list of all the projects that this user is working under
	 * @return
	 */
	public List<Project> getProjects() {
		return this.projectList;
	}

	/**
	 * Removes a given project from this developer's list of projects.
	 * Also removes the developer from all activities associated with that project
	 * @param project
	 */
	public void removeProject(Project project) {
		this.projectList.remove(project);
		
		List<Activity> actList = this.activityList.stream().filter(a -> a.getProject().equals(project)).collect(Collectors.toList());
		for(Activity act : actList) {
			act.removeDeveloper(this);
			this.activityList.remove(act);
		}
	}

	/**
	 * Promotes a developer from assisting on a project to being directly associated with the project.
	 * This will cause the developer to be removed from the assistant developer list on all activities under the given project,
	 * adding them instead to the normal developer list
	 * @param project The project which the developer was added to
	 */
	public void promoteFromAssistant(Project project) {
		this.activityList.stream().filter(a -> a.getProject().equals(project)).forEach(a -> a.migrateDeveloper(this));
	}
}
