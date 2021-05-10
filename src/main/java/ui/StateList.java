package ui;

public class StateList {
	public static final ControllerState LOGIN = new LoginState();
	public static final ControllerState MAIN = new MainState();
	public static final ControllerState SELECTPROJECT = new SelectProjectState();
	public static final ControllerState PROJECT = new ProjectState();
	public static final ControllerState NEWPROJECT = new NewProjectState();
	public static final ControllerState VIEWDEVS = new ViewDevsState();
	public static final ControllerState MANAGEPROJECT = new ManageProjectState();
}
