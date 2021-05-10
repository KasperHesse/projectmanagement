package schedulingapp;

/**¨
 * This class is intended to make Projects more easily manageable (in testers)
 * @author Kasper Hesse, s183735
 *
 */
public class ProjectHelper {
	
	private SchedulingApp schedulingApp;
	private Project mostRecent;
	
	public ProjectHelper(SchedulingApp schedulingApp) {
		this.schedulingApp = schedulingApp;
		this.mostRecent = null;
	}
	
	/**
	 * Returns a handle to a project with a given name. Creates the project if no project has this name so far
	 * @param name
	 * @return
	 */
	public Project getProject(String name) {
		if(!schedulingApp.hasProjectNamed(name)) {
			schedulingApp.createProject(name);
		}
		mostRecent = schedulingApp.getProjectByName(name);
		return mostRecent;
	}

	/**
	 * Returns a handle to the most recently accessed project.
	 */
	public Project getProject() {
		if(mostRecent == null) {
			mostRecent = getProject("Sample Projcet");
		}
		return mostRecent;
	}
	
	
}
