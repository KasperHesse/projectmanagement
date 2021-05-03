package ui;

import schedulingapp.*;
import static ui.ControllerState.*;
import java.util.*;
import java.beans.*;
import static ui.ControllerMessages.*;

import dto.*;
import dto.ProjectInfo;

/**
 * The controller in the MVC-based user interface.
 * @author kaspe
 *
 */
public class Controller implements PropertyChangeListener {
	SchedulingApp model;
	SchedulingAppCLI view;
	ControllerState state;
	Stack<ControllerState> stateStack;
	
	List<ProjectInfo> projectList;
	List<ActivityInfo> activityList;
	
	ProjectInfo activeProject;
	ActivityInfo activeActivity;
	
	private final String[] MAINOPTIONS = new String[] {"Logout", 
			"Show projects", 
			"View free developers"
	};
	
	private final String[] PROJECTOPTIONS = new String[] {"Choose activity",
			"Manage project"
	};
	
	private final String[] MANAGEPROJECTOPTIONS = new String[] {"Add developer",
			"Remove developer",
			"Change/assign project manager",
			"Get time report"
	};
	
	private final String[] ACTIVITYOPTIONS = new String[] {"Register time", 
			"Edit registered time", 
			"Seek assistance", 
			"View registered time", 
			"Remove assisant developer (PM)"
	};
	

	
	public Controller() {
		model = new SchedulingApp();
		view = new SchedulingAppCLI();
		initialSetup(); //Must perform setup before adding observer
		
		model.addObserver(this);
		this.state = sLOGIN;
		stateStack = new Stack<ControllerState>();

		try {
			mainLoop();
		} catch(Exception e) {
			view.showError(e.getMessage());
			System.exit(1);	
		}
	}

	public static void main(String[] args) {
		new Controller();
	}
	
	/**
	 * Performs the initial setup of the system, such as generating developers and projects
	 */
	private void initialSetup() {
		DeveloperInfo d = model.createDeveloper("Kasper Hesse", "kahe");
		model.login("kahe");
		
		ProjectInfo p = model.createProject("my first project");
		model.setProjectManager(p, d);
		model.createActivity(p, "my first activity");
		this.projectList = model.getAllProjects();
		
		model.logout();
		
		showWelcomeMessage();
		

	}
	
	/**
	 * Displays a welcome message when the app is booted, or whenever the user logs out
	 */
	private void showWelcomeMessage() {
		System.out.println("Welcome to the Scheduling Application. Please enter your initials to login");
	}
	
	/**
	 * The main control loop for the CLI application
	 * @throws Exception If the system enters an unknown state
	 */
	private void mainLoop() throws Exception {
		Scanner consoleParser = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String[] tokens = consoleParser.nextLine().split(" ");
			
			if(state == sLOGIN) { //Must run before the if-statement below
				processInputLogin(tokens);
				continue;
			}
			
			if(tokens[0].equalsIgnoreCase("back")) {
				goBack();
				continue;
			} else if (tokens[0].equalsIgnoreCase("help")) {
				showHelp();
				continue;
			} else if (tokens[0].equalsIgnoreCase("home")) {
				stateStack.clear();
				setState(sMAIN);
				continue;
			}
			
			switch(state) {
			case sMAIN: processInputMain(tokens); break;
			case sSELECTPROJECT: processInputSelectProject(tokens); break;
			case sPROJECT: processInputProject(tokens); break;
			case sSELECTACTIVITY: processInputSelectActivity(tokens); break;
			case sACTIVITY: processInputActivity(tokens); break;
			case sREGISTERTIME: processInputRegisterTime(tokens); break;
			default: throw new Exception("An unknown error occured");
			}
		}
	}

	/**
	 * Shows the help message for the current state
	 */
	private void showHelp() {
		view.showError("showHelp not yet implemented");
		
	}

	/**
	 * Goes back to the previous state
	 */
	private void goBack() {
		if(stateStack.isEmpty()) {
			view.showError("Cannot go further back");
			return;
		}
		setState(stateStack.pop(), false);
	}
	
	/**
	 * Used to process input when the system is in the LOGIN state
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputLogin(String[] tokens) {
		//Invalid input checks
		if(tokens.length != 1 || tokens[0].length() != 4) {
			view.showError(ControllerMessages.E_ENTER_INITIALS);
		} else {
			//Login attempt
			if(!model.login(tokens[0].toLowerCase())) {
				view.showError(ControllerMessages.E_NO_SUCH_INITIALS);
			} else {
				setState(sMAIN);
			}
		}
	}
	
	/**
	 * Used to process input when the system is in the MAIN state (logged in, but no actions performed yet)
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputMain(String[] tokens) {
		int token = validateInteger(tokens, 2);
		//Process valid input
		switch(token) {
		case -1:
			break;
		case 0: 
			model.logout();
			setState(sLOGIN);
			break;
		case 1:
			setState(sSELECTPROJECT);
			break;
		case 2:
//			this.state = sVIEWDEVS;
//			view.showOptions(model.getAvailableDevelopers());
			view.showError("Not yet implemented");
			break;

		default:
			view.showError(ControllerMessages.E_INVALID_INPUT);
			break;
		}
	}
	
	/**
	 * Used to process input when system is in the SELECTPROJECT state (selecting a project)
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputSelectProject(String[] tokens) {
		int token = validateInteger(tokens, projectList.size()-1);
		if(token == -1) {
			return;
		}
		model.setActiveProject(projectList.get(token));
		this.activityList = model.getActiveProjectActivities();
		setState(sPROJECT);
	}
	
	/**
	 * Processes input when the PROJECT state is active (select activity or manage project)
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputProject(String[] tokens) {
		int token = validateInteger(tokens, PROJECTOPTIONS.length - 1);
		switch(token) {
		case 0:
			setState(sSELECTACTIVITY);
			break;
		case 1:
			setState(sMANAGEPROJECT);
			break;
		}
	}
	
	/**
	 * Processes input when the SELECTACTIVITY state is active
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputSelectActivity(String[] tokens) {
		int token = validateInteger(tokens, activityList.size()-1);
		if(token == -1) {
			return;
		}
		model.setActiveActivity(activityList.get(token));
		setState(sACTIVITY);
	}
	
	/**
	 * Processes input when the ACTIVITY state is active (edit, register time etc)
	 * @param tokens
	 */
	private void processInputActivity(String[] tokens) {
		int token = validateInteger(tokens, 4);
		switch(token) {
		case 0:
			setState(sREGISTERTIME);
			break;
		case 1:
			setState(sEDITREGISTEREDTIME);
			break;
		case 2:
			setState(sASSISTANCE);
			break;
		case 3:
			setState(sVIEWTIME);
			break;
		case 4:
			setState(sREMOVEASSTDEV);
			break;
		}
	}
	
	private void processInputRegisterTime(String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours < 0.5 || hours > 24) {
			view.showError(ControllerMessages.E_INVALID_HOURS);
			return;
		}
		model.registerTimeOnActivity(date, hours);
	}
	
	private void processInputEditRegisteredTime(String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours < 0.5 || hours > 24) {
			view.showError(E_INVALID_HOURS);
			return;
		}
		model.editTimeOnActivity(date, hours);
	}
	
	
	/**
	 * Sets the state of the controller, and calls any state-specific functionality that should always be performed.
	 * This version should onlyy be called when adding items to the state stack
	 * @param state The new state of the system
	 */
	private void setState(ControllerState state) {
		setState(state, true);
	}
	
	/**
	 * Sets the state of the controller, and calls any state-specific functionality. 
	 * @param state The new state of the system
	 * @param shouldPush Whether the current state should be pushed onto the state stack
	 */
	private void setState(ControllerState state, boolean shouldPush) {
		if(shouldPush) {stateStack.push(this.state); }
		this.state = state;
		switch(state) {
		case sLOGIN:
			model.logout();
			showWelcomeMessage();
			break;
		case sMAIN:
			view.showOptions(MAINOPTIONS);
			break;
		case sSELECTPROJECT:
			view.showOptions(projectList);
			break;
		case sPROJECT:
			view.showOptions(PROJECTOPTIONS);
			break;
		case sMANAGEPROJECT:
			view.showOptions(MANAGEPROJECTOPTIONS);
			break;
		case sSELECTACTIVITY:
			view.showOptions(activityList);
			break;
		case sACTIVITY:
			view.showOptions(ACTIVITYOPTIONS);
			break;
		case sREGISTERTIME:
			view.showMessage(ControllerMessages.I_REGISTER_TIME);
			break;
		case sEDITREGISTEREDTIME:
			view.showMessage(ControllerMessages.I_EDIT_REGISTERED_TIME);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Verifies that the only input received is a valid integer of a maximum size. Will output an error if more than 1 value was input
	 * @param tokens The tokens received on the command line
	 * @param max The largest allowed value (inclusive)
	 * @return The valid integer received, or -1 if no valid integer was present
	 */
	private int validateInteger(String[] tokens, int max) {
		int tokenInt = Utils.validateInteger(tokens[0], max);
		//Input validation
		if(tokens.length != 1 || tokenInt == -1) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			tokenInt = -1;
		}
		return tokenInt;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("user")) {
			userChangedEvent(evt);
		} else if (evt.getPropertyName().equals("project")) {
			projectChangedEvent(evt);
		} else if(evt.getPropertyName().equals("activity")) {
			activeActivity = (ActivityInfo) evt.getNewValue();
			view.showMessage(String.format("Selected activity \"%s\"", activeActivity.getName()));
		} else if(evt.getPropertyName().equals("time")) {
			view.showMessage(String.format("Registered %.1f hours on \"%s\"", (Double) evt.getNewValue(), activeActivity));
		}
	}
	
	/**
	 * Processes an event where the current user has changed.
	 * @param evt The event that was fired
	 */
	void userChangedEvent(PropertyChangeEvent evt) {
		if(evt.getNewValue() == null) {
			view.showMessage("Logged out");
		} else {
			view.showMessage(String.format("Logged in. Welcome, %s.", ((DeveloperInfo) evt.getNewValue()).getName()));
		}
	}
	
	/**
	 * Processes an event where the active project has changed
	 * @param evt The event that was fired
	 */
	void projectChangedEvent(PropertyChangeEvent evt) {
		activeProject = (ProjectInfo) evt.getNewValue();
		view.showMessage(String.format("Selected project \"%s\"", activeProject.getName()));
	}
}

