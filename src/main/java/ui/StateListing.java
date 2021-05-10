package ui;

/**
 * @author Kasper Hesse, s183735
 *
 */
public class StateListing {
	public static final ControllerState LOGIN = new LoginState();
	public static final ControllerState MAIN = new MainState();
	public static final ControllerState SELECTPROJECT = new SelectProjectState();
	public static final ControllerState PROJECT = new ProjectState();
	public static final ControllerState NEWPROJECT = new NewProjectState();
	public static final ControllerState VIEWDEVS = new ViewAvailableDevelopersState();
	public static final ControllerState MANAGEPROJECT = new ManageProjectState();
	public static final ControllerState SELECTACTIVITY = new SelectActivityState();
	public static final ControllerState ACTIVITY = new ActivityState();
	public static final ControllerState REGISTERTIME = new RegisterTimeState();
	public static final ControllerState EDITREGISTEREDTIME = new EditRegisterTimeState();
	public static final ControllerState VIEWTIME = new ViewTimeState();
	public static final ControllerState SEEKASSISTANCE = new SeekAssistanceState();
	public static final ControllerState REMOVEASSTDEV = new RemoveAssistantDeveloperState();
	public static final ControllerState CHANGEBUDGETHOURS = new ChangeBudgetHoursState();
	public static final ControllerState ADDDEVACT = new AddDevToActivityState();
	public static final ControllerState REMOVEDEVACT = new RemoveDeveloperFromActivityState();
	public static final ControllerState CHANGESTARTWEEK = new ChangeStartWeekState();
	public static final ControllerState CHANGESTOPWEEK = new ChangeStopWeekState();
	public static final ControllerState NEWACTIVITY = new NewActivityState();
	public static final ControllerState ADDDEVPROJ = new AddDeveloperToProjectState();
	public static final ControllerState REMOVEDEVPROJ = new RemoveDeveloperFromProjectState();
	public static final ControllerState CHANGEPROJMAN = new ChangeProjectManagerState();
	
}
