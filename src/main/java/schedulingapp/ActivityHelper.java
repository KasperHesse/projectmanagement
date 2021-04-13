package schedulingapp;

/**¨
 * This class is intended to make Projects more easily manageable (in testers)
 * @author kaspe
 *
 */
public class ActivityHelper {
	
	private SchedulingApp schedulingApp;
	private Activity mostRecent;
	
	public ActivityHelper(SchedulingApp schedulingApp) {
		this.schedulingApp = schedulingApp;
		this.mostRecent = null;
	}
	
	/**
	 * Returns a handle to an activity with a given name under a project. Creates the activity if no activity has this name so far
	 * @param project The project under which the activity should be placed
	 * @param name The name of the activity
	 * @return A handle to the activity under the project
	 */
	public Activity getActivity(Project project, String name) {
		if(!project.hasActivityNamed(name)) {
			project.createActivity(name);
		}
		mostRecent = project.getActivityByName(name);
		return mostRecent;
	}

	/**
	 * Returns a handle to the most recently accessed activity under *any* project.
	 * Creates a sample project and sample activity if this is not possible
	 */
	public Activity getActivity() {
		if(mostRecent == null) {
			schedulingApp.createProject("Sample project");
			schedulingApp.getProjectByName("Sample project").createActivity("Sample activity");
			mostRecent = schedulingApp.getProjectByName("Sample project").getActivityByName("Sample activity");
		}
		return mostRecent;
	}
}
