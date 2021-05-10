package ui;

import schedulingapp.*;
import static ui.ControllerState.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.beans.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import static ui.ControllerMessages.*;

import dto.*;
import dto.ProjectInfo;

/**
 * The controller in the MVC-based user interface.
 * @author Kasper Hesse, s183735
 *
 */
public class Controller implements PropertyChangeListener {
	SchedulingApp model;
	SchedulingAppCLI view;
	ControllerState state;
	Stack<ControllerState> stateStack;
	
	List<ProjectInfo> projectList;
	List<ActivityInfo> activityList;
	List<DeveloperInfo> developerList;
	
	ProjectInfo activeProject;
	ActivityInfo activeActivity;
	DeveloperInfo currentUser;
	
	
	private final String[] MAINOPTIONS = new String[] {"Logout", 
			"Show projects", 
			"New project",
			"View free developers"
	};
	
	private final String[] PROJECTOPTIONS = new String[] {"Choose activity",
			"Manage project"
	};
	
	private final String[] MANAGEPROJECTOPTIONS = new String[] {"Add developer (PM)",
			"Remove developer (PM)",
			"Change/assign project manager",
			"Get time report (PM)",
			"Add new activity (PM)"
	};
	
	private final String[] ACTIVITYOPTIONS = new String[] {"Register time", 
			"Edit registered time", 
			"View registered time", 
			"Seek assistance", 
			"Assign developer to activity (PM)",
			"Remove developer from activity (PM)",
			"Remove assisant developer (PM)",
			"Change hours budgeted (PM)",
			"Change start date",
			"Change stop date"
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
	
	//Move this to the scheduling app for easier data management
	/**
	 * Performs the initial setup of the system, such as generating developers and projects
	 */
	private void initialSetup() {
		model.presentationSetup();		
		view.showMessage(I_WELCOME_MSG);
	}

	
	/**
	 * The main control loop for the CLI application
	 * @throws Exception If the system enters an unknown state
	 */
	private void mainLoop() throws Exception {
		Scanner consoleParser = new Scanner(System.in);
		Pattern regex = Pattern.compile("(?:[^\\s,\"]+|\"[^\"]*\")+"); //Split at commas + spaces not in quotations. From https://stackoverflow.com/questions/16261635/
		Matcher matcher = regex.matcher("");
		ArrayList<String> matches = new ArrayList<>();
		while(true) {
			System.out.print("> ");
			
			String line = consoleParser.nextLine();
//			matcher = regex.matcher(line);
			matcher.reset(line);
			matches.clear();

			while(matcher.find()) {
				matches.add(matcher.group());
			}
			String[] tokens = matches.toArray(new String[0]);
						
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
			case sNEWPROJECT: processInputNewProject(tokens); break;
			case sSELECTACTIVITY: processInputSelectActivity(tokens); break;
			case sACTIVITY: processInputActivity(tokens); break;
			case sREGISTERTIME: processInputRegisterTime(tokens); break;
			case sEDITREGISTEREDTIME: processInputEditRegisteredTime(tokens); break;
			case sVIEWTIME: processInputViewTime(tokens); break;
			case sASSISTANCE: processInputSeekAssistance(tokens); break;
			case sREMOVEASSTDEV: processInputRemoveAsstDev(tokens); break;
			case sCHANGEBUDGETHOURS: processInputChangeBudgetHours(tokens); break;
			case sADDDEVPROJ: processInputAddDevProject(tokens); break;
			case sREMOVEDEVPROJ: processInputRemoveDevProject(tokens); break;
			case sMANAGEPROJECT: processInputManageProject(tokens); break;
			case sCHANGEPROJMAN: processInputChangeProjMan(tokens); break;
			case sNEWACTIVITY: processInputNewActivity(tokens); break;
			case sADDDEVACT: processInputAddDevActivity(tokens); break;
			case sREMOVEDEVACT: processInputRemoveDevActivity(tokens); break;
			case sCHANGESTARTWEEK: processInputChangeStartWeek(tokens); break;
			case sCHANGESTOPWEEK: processInputChangeStopWeek(tokens); break;
			default: consoleParser.close(); throw new Exception("An unknown error occured");
			}
		}
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
			view.showMessage(I_WELCOME_MSG);
			break;
		case sMAIN:
			view.showOptions(MAINOPTIONS);
			break;
		case sSELECTPROJECT:
			this.projectList = model.getVisisbleProjects();
			view.showOptions(projectList);
			break;
		case sPROJECT:
			view.showMessage(activeProject.getInfoString());
			view.showOptions(PROJECTOPTIONS);
			break;
		case sMANAGEPROJECT:
			view.showOptions(MANAGEPROJECTOPTIONS);
			break;
		case sNEWPROJECT:
			view.showMessage(I_NEW_PROJECT);
			break;
		case sSELECTACTIVITY:
			view.showOptions(activityList);
			break;
		case sACTIVITY:
			view.showMessage(activeActivity.getInfoString());
			view.showOptions(ACTIVITYOPTIONS);
			break;
		case sREGISTERTIME:
			view.showMessage(ControllerMessages.I_REGISTER_TIME);
			break;
		case sEDITREGISTEREDTIME:
			view.showMessage(ControllerMessages.I_EDIT_REGISTERED_TIME);
			break;
		case sVIEWTIME:
			view.showMessage(I_VIEW_TIME);
			break;
		case sASSISTANCE:
			view.showMessage(I_SEEK_ASSISTANCE);
			break;
		case sREMOVEASSTDEV:
			developerList = model.getAssistantDeveloperList();
			if(developerList.isEmpty()) {
				view.showMessage(I_NO_DEVELOPERS_REMOVE);
				goBack();
				break;
			}
			view.showMessage(I_REMOVE_ASST_DEV);
			view.showOptions(developerList);
			break;
		case sCHANGEBUDGETHOURS:
			view.showMessage(I_CHANGE_BUDGET_HOURS);
			view.showMessage(String.format("This activity currently has %d hours budgeted", model.getActivityTimeBudget()));
			break;
		case sADDDEVACT:
			developerList = model.getProjectDeveloperList();
			developerList.removeAll(model.getActivityDeveloperList());
			if(developerList.isEmpty()) {
				view.showMessage(I_NO_DEVELOPERS_ADD);
				goBack();
				break;
			}
			view.showMessage(I_ADD_DEV_ACT);
			view.showOptions(developerList);
			break;
		case sREMOVEDEVACT:
			developerList = model.getActivityDeveloperList();
			developerList.remove(currentUser);
			if(developerList.isEmpty()) {
				view.showMessage(I_NO_DEVELOPERS_REMOVE);
				goBack();
				break;
			}
			view.showMessage(I_REMOVE_DEV_ACT);
			view.showOptions(developerList);
			break;
		case sNEWACTIVITY:
			view.showMessage(I_NEW_ACTIVITY);
			break;
		case sCHANGESTARTWEEK:
			view.showMessage(I_CHANGE_START_WEEK);
			break;
		case sCHANGESTOPWEEK:
			view.showMessage(I_CHANGE_STOP_WEEK);
			break;
		case sADDDEVPROJ:
			view.showMessage(I_ADD_DEV_PROJ);
			break;
		case sREMOVEDEVPROJ:
			developerList = model.getProjectDeveloperList();
			view.showMessage(I_REMOVE_DEV_PROJ);
			view.showOptions(developerList);
			break;
		case sCHANGEPROJMAN:
			view.showMessage(I_CHANGE_PROJ_MAN);
			break;
		case sVIEWDEVS:
			view.showMessage(I_VIEW_DEVS);
			developerList = model.getAvailableDevelopers();
			view.showOptions(developerList, "");
			goBack();
		default:
			break;
		}
	}

	/**
	 * Shows the help message for the current state
	 */
	private void showHelp() {
		view.showMessage(I_HELP_MSG);
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
				view.showError(ControllerMessages.E_INVALID_INITIALS);
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
		case 0: 
			model.logout();
			setState(sLOGIN);
			break;
		case 1:
			setState(sSELECTPROJECT);
			break;
		case 2:
			setState(sNEWPROJECT);
			break;
		case 3:
			setState(sVIEWDEVS);
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
		if(token == Integer.MIN_VALUE) {
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
			if(activityList.size() == 0) {
				view.showError(E_NO_ACTIVITIES);
				break;
			}
			setState(sSELECTACTIVITY);
			break;
		case 1:
			setState(sMANAGEPROJECT);
			break;
		}
	}
	
	/**
	 * Processes input when the ADDPROJECT state is active
	 * @param tokens The input tokens obtained from the command line.
	 */
	private void processInputNewProject(String[] tokens) {
		//Parse string for tokens, find the token after "=" and use as the key
		String name = "";
		String start = "";
		String stop = "";
		String pm = "";
		for(String token : tokens) {
			try {
				if(token.contains("name") && name.equals("")) {
					name = findValueAfterKey(token, "name");
				}
				if(token.contains("start") && start.equals("")) {
					start = findValueAfterKey(token, "start");
				}
				if(token.contains("stop") && stop.equals("")) {
					stop = findValueAfterKey(token, "stop");
				}
				if(token.contains("pm") && pm.equals("")) {
					pm = findValueAfterKey(token, "pm");
				}
			} catch (IllegalArgumentException e) { //One of the token strings did not match the required format
				view.showError("Error on '" + token + "'. " + E_KEYVAL_BADFORMAT);
				return;
			} catch (NoSuchElementException e) { //Key was not present in the string
				view.showError("Error on '" + token + "'. " + E_KEY_NOT_PRESENT);
			}
		}
		
		if(name.equals("")) {
			view.showError(E_NEW_PROJ_MUST_HAVE_NAME);
			return;
		}
		
		//Check if the PM (if given) is valid
		DeveloperInfo projMan = null;
		if(!pm.equals("")) {
			projMan = model.getDeveloperWithInitials(pm);
			if(projMan == null || !projMan.getAvailableFlag())  {
				view.showError(E_DEV_MAX_PROJECTS);
				return;
			}
		}
		
		//Check if start/stop dates are valid
		Calendar startDate = null;
		Calendar stopDate = null;
		if(!start.equals("")) {
			startDate = Utils.validateDate(start);
			if(startDate == null) {
				view.showError("Error on start date. " + E_INVALID_DATE);
			}
		}
		if(!stop.equals("")) {
			stopDate = Utils.validateDate(stop);
			if(stopDate == null) {
				view.showError("Error on stop date. " + E_INVALID_DATE);
			}
		}
		if(startDate != null && stopDate != null && !startDate.before(stopDate)) {
			view.showError(E_DATES_INVALID);
			return;
		}
		try {
			model.addNewProject(name, startDate, stopDate, projMan);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Searches a string of the type {@code <key>=<value>} for a given key, and returns the value following that key
	 * @param str The string to search in
	 * @param key The key to search for in the string
	 * @return The value found after that key, if the key was present
	 * @throws IllegalArgumentException If the string is not of the type {@code <key>=<value>}
	 */
	private String findValueAfterKey(String str, String key) throws NoSuchElementException, IllegalArgumentException {
		String str2 = str.replace("\"", "");
		String matchString = String.format("%s=.+", key);
		if(!str2.matches(matchString)) { //key, followed by =, followed by anything
			throw new IllegalArgumentException("Input string '" + str + "' was not parseable");
		}
		String valueStr = str2.substring(str2.indexOf("=")+1);
		return valueStr;	
	}
	
	/**
	 * Processes input when the SELECTACTIVITY state is active
	 * @param tokens The input tokens obtained from the command line
	 */
	private void processInputSelectActivity(String[] tokens) {
		int token = validateInteger(tokens, activityList.size()-1);
		if(token == Integer.MIN_VALUE) {
			return;
		}
		model.setActiveActivity(activityList.get(token));
		this.activeActivity = model.getActiveActivity();
		setState(sACTIVITY);
	}
	
	/**
	 * Processes input when the ACTIVITY state is active (edit, register time etc)
	 * @param tokens
	 */
	private void processInputActivity(String[] tokens) {
		int tokenInt = validateInteger(tokens, ACTIVITYOPTIONS.length-1);
		switch(tokenInt) {
		case 0:
			setState(sREGISTERTIME);
			break;
		case 1:
			setState(sEDITREGISTEREDTIME);
			break;
		case 2:
			setState(sVIEWTIME);
			break;
		case 3:
			setState(sASSISTANCE);
			break;
		case 4:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sADDDEVACT);
			break;
		case 5:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sREMOVEDEVACT);
			break;
		case 6:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sREMOVEASSTDEV);
			break;
		case 7:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sCHANGEBUDGETHOURS);
			break;
		case 8:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sCHANGESTARTWEEK);
			break;
		case 9:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sCHANGESTOPWEEK);
			break;
		}
	}
	
	
	/**
	 * Processes input when the REGISTERTIME state is active
	 * @param tokens
	 */
	private void processInputRegisterTime(String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], 0.5, 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_HOURS);
			return;
		}
		try {
			model.registerTimeOnActivity(date, hours);
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Processes input when the EDITREGISTEREDTIME state is active
	 * @param tokens
	 */
	private void processInputEditRegisteredTime(String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], -24, 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours == Integer.MIN_VALUE) {
			view.showError(E_INVALID_HOURS);
			return;
		}
		try {
			model.editTimeOnActivity(date, hours);
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Processes input when the VIEWTIME state is active
	 * @param tokens
	 */
	private void processInputViewTime(String[] tokens) {
		if(tokens.length != 1) {
			view.showError(E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		if(date == null) {
			view.showError(E_INVALID_DATE);
			return;
		}
		try {
			view.showMessage(String.format("You have %.1f hours registered on that date", model.getHoursOnActivity(date)));
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Processes input when in the ASSISTANCE state
	 * @param tokens
	 */
	private void processInputSeekAssistance(String[] tokens) {
		DeveloperInfo d = validateInitials(tokens);
		if(d == null) {
			return;
		}
		try {
			model.addAssistantDeveloper(d);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Processes input when in the REMOVEASSTDEV state
	 * @param tokens
	 */
	private void processInputRemoveAsstDev(String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], MANAGEPROJECTOPTIONS.length);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		try {
			model.removeAssistantDeveloper(developerList.get(tokenInt));
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputChangeBudgetHours(String[] tokens) {
		int input = Utils.validateInteger(tokens[0], Integer.MAX_VALUE);
		if(tokens.length != 1 || input == Integer.MIN_VALUE) {
			view.showError(E_INVALID_BUDGET);
			return;
		}
		try {
			model.setActivityTimeBudget(input);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Process input when in the ADDDEVACT state
	 * @param tokens
	 */
	private void processInputAddDevActivity(String[] tokens) {
		int input = validateInteger(tokens, developerList.size()-1);
		if(input == Integer.MIN_VALUE) {
			return;
		}
		try {
			model.addDeveloperToActivity(developerList.get(input));
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Process input when in the REMOVEDEVACT state
	 * @param tokens
	 */
	private void processInputRemoveDevActivity(String[] tokens) {
		int input = validateInteger(tokens, developerList.size()-1);
		if(input == Integer.MIN_VALUE) {
			return;
		}
		try {
			model.removeDeveloperFromActivity(developerList.get(input));
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputChangeStartWeek(String[] tokens) {
		Calendar date = Utils.validateDate(tokens[0]);
		if(tokens.length != 1 || date == null) {
			view.showError(E_INVALID_DATE);
		}
		try {
			model.setActivityStartDate(date);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputChangeStopWeek(String[] tokens) {
		Calendar date = Utils.validateDate(tokens[0]);
		if(tokens.length != 1 || date == null) {
			view.showError(E_INVALID_DATE);
		}
		try {
			model.setActivityStopDate(date);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputManageProject(String[] tokens) {
		int tokenInt = validateInteger(tokens, MANAGEPROJECTOPTIONS.length-1);
		switch(tokenInt) {
		case 0:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sADDDEVPROJ);
			break;
		case 1:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sREMOVEDEVPROJ);
			break;
		case 2:
			if(projectHasPM() && !ensureUserIsPM()) {
				break;
			}
			setState(sCHANGEPROJMAN);
			break;
		case 3:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sGETTIMEREPORT);
			view.showError("Not yet implemented");
			goBack();
			break;
		case 4:
			if(!ensureUserIsPM()) {
				break;
			}
			setState(sNEWACTIVITY);
		}
	}
	
	private void processInputAddDevProject(String[] tokens) {
		//Check if system contains a dev with those initials
		DeveloperInfo d = validateInitials(tokens);
		if(d == null) {
			return;
		}
		try {
			model.addDeveloperToProject(d);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputRemoveDevProject(String[] tokens) {
		//Project managers cannot remove themselves - must promote someone else to be PM
		int input = Utils.validateInteger(tokens[0], developerList.size()-1);
		if(tokens.length != 1 || input == Integer.MIN_VALUE) {
			view.showError(E_INVALID_INPUT);
			return;
		}
		try {
			model.removeDeveloperFromProject(developerList.get(input));
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputChangeProjMan(String[] tokens) {
		//Get initials. If they match someone, make that person the PM
		DeveloperInfo d = validateInitials(tokens);
		if(d == null) {
			return;
		}
		try {
			model.setProjectManager(d);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputNewActivity(String[] tokens) {
		//Parse string for tokens, find the token after "=" and use as the key
		String name = "";
		String start = "";
		String stop = "";
		String hours = "";
		for(String token : tokens) {
			try {
				if(token.contains("name") && name.equals("")) {
					name = findValueAfterKey(token, "name");
				}
				if(token.contains("start") && start.equals("")) {
					start = findValueAfterKey(token, "start");
				}
				if(token.contains("stop") && stop.equals("")) {
					stop = findValueAfterKey(token, "stop");
				}
				if(token.contains("hours") && hours.equals("")) {
					hours = findValueAfterKey(token, "hours");
				}
			} catch (IllegalArgumentException e) { //One of the token strings did not match the required format
				view.showError("Error on '" + token + "'. " + E_KEYVAL_BADFORMAT);
				return;
			} catch (NoSuchElementException e) { //Key was not present in the string
				view.showError("Error on '" + token + "'. " + E_KEY_NOT_PRESENT);
			}
		}
		
		if(name.equals("")) {
			view.showError(E_NEW_PROJ_MUST_HAVE_NAME);
			return;
		}
		
		//Check if the PM (if given) is valid
		Integer hoursToBudget = null;
		if(!hours.equals("")) {
			hoursToBudget = Utils.validateInteger(hours, Integer.MAX_VALUE);
			if(hoursToBudget == Integer.MIN_VALUE)  {
				view.showError(E_INVALID_BUDGET);
				return;
			}
		}
		
		//Check if start/stop dates are valid
		Calendar startDate = null;
		Calendar stopDate = null;
		if(!start.equals("")) {
			startDate = Utils.validateDate(start);
			if(startDate == null) {
				view.showError("Error on start date. " + E_INVALID_DATE);
			}
		}
		if(!stop.equals("")) {
			stopDate = Utils.validateDate(stop);
			if(stopDate == null) {
				view.showError("Error on stop date. " + E_INVALID_DATE);
			}
		}
		if(startDate != null && stopDate != null && !startDate.before(stopDate)) {
			view.showError(E_DATES_INVALID);
			return;
		}
		try {
			model.addNewActivity(name, startDate, stopDate, hoursToBudget);
			goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}
	
	private void processInputGetTimeReport(String[] tokens) {
		
	}
	
	
	/**
	 * Verifies if the current user of the system is the project manager of the active project. Outputs an error message if this is not the case.
	 * @return True if they are the PM, false otherwise
	 */
	private boolean ensureUserIsPM() {
		if(!model.userIsProjectManager()) {
			view.showError(E_NOT_PROJ_MAN);
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether the active project has a project manager set.
	 * @return True if the project has as PM, false otherwise
	 */
	private boolean projectHasPM() {
		return model.getProjectManager() != null;
	}
	
	
	/**
	 * Verifies that the only input received is a valid integer of a maximum size. Will output an error if more than 1 value was input
	 * @param tokens The tokens received on the command line
	 * @param max The largest allowed value (inclusive)
	 * @return The valid integer received, or Integer.MIN_VALUE if no valid integer was present
	 */
	private int validateInteger(String[] tokens, int max) {
		int tokenInt = Utils.validateInteger(tokens[0], max);
		//Input validation
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			tokenInt = Integer.MIN_VALUE;
		}
		return tokenInt;
	}
	
	/**
	 * Verifies that the only input received was a valid set of initials. Prints an error if no developer was found
	 * @param tokens The tokens received on the command line
	 * @return A handle to information on the given developer, or null if no developer was found.
	 */
	private DeveloperInfo validateInitials(String[] tokens) {
		DeveloperInfo d = model.getDeveloperWithInitials(tokens[0]);
		if(tokens.length != 1 || tokens[0].length() != 4 || d == null) {
			view.showError(E_INVALID_INITIALS);
			return null;
		}
		return d;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("user")) { //User has changed
			userChangedEvent(evt);
			
		} else if (evt.getPropertyName().equals("project")) { //Active project has changed
			projectChangedEvent(evt);
			
		} else if(evt.getPropertyName().equals("activity")) { //Active activity has changed
			activeActivity = (ActivityInfo) evt.getNewValue();
			view.showMessage(String.format("Selected activity \"%s\"", activeActivity.getName()));
			
		} else if(evt.getPropertyName().equals("time")) { //Time registration changed
			if((Double) evt.getOldValue() == null) {
				view.showMessage(String.format("Registered %.1f hours on \"%s\"", (Double) evt.getNewValue(), activeActivity));
			} else {
				view.showMessage(String.format("Edited hours on \"%s\". Previous registration was %.1f, new registration is %.1f", activeActivity, (Double) evt.getOldValue(), (Double) evt.getNewValue()));
			}
	
		} else if(evt.getPropertyName().equals("asstdev")) { //Assistant developer changed
			if(evt.getNewValue() != null) {
				view.showMessage(String.format("%s was added to the activity as an assistant developer", ((DeveloperInfo) evt.getNewValue()).getName()));
			} else {
				view.showMessage(String.format("%s was removed as an assistant developer", ((DeveloperInfo) evt.getOldValue()).getName()));
			}
		
		} else if (evt.getPropertyName().equals("budget")) { //Hours budgetted
			view.showMessage(String.format("This activity now has %d hours budgetted", (int) evt.getNewValue()));
			activeActivity = model.getActiveActivity();
			
		} else if (evt.getPropertyName().equals("devlist")) { //Developer list changed
			if(evt.getNewValue() != null) {
				view.showMessage(String.format("%s has been added as a developer", ((DeveloperInfo) evt.getNewValue()).getName()));
			} else {
				view.showMessage(String.format("%s has been removed as a developer", ((DeveloperInfo) evt.getOldValue()).getName()));
			}
			developerList = model.getProjectDeveloperList();
			
		} else if (evt.getPropertyName().equals("projman")) { //Project manager changed
			view.showMessage(String.format("The new project manager of this project is %s", ((DeveloperInfo) evt.getNewValue()).getName()));
			activeProject = model.getActiveProject();
		
		} else if (evt.getPropertyName().equals("startdate")) { //Activity start date changed
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			String date = formatter.format(((Calendar) evt.getNewValue()).getTime());
			view.showMessage(String.format("Activity start date has been changed to %s", date));
			activityList = model.getActiveProjectActivities();
			activeActivity = model.getActiveActivity();
		} else if (evt.getPropertyName().equals("stopdate")) { //Activity stop date changed
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			String date = formatter.format(((Calendar) evt.getNewValue()).getTime());
			view.showMessage(String.format("Activity stop date has been changed to %s", date));
			activityList = model.getActiveProjectActivities();
			activeActivity = model.getActiveActivity();
		
		} else if (evt.getPropertyName().equals("projlist")) { //Project list has been updated / new project
			view.showMessage(String.format("Project '%s' was added to the system", (ProjectInfo) evt.getNewValue()));
			
		} else if (evt.getPropertyName().equals("actlist")) { //Activity was added under current project
			view.showMessage(String.format("Activity '%s' was added under the current project", ((ActivityInfo) evt.getNewValue()).getName()));
			activityList = model.getActiveProjectActivities();
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
			currentUser = (DeveloperInfo) evt.getNewValue();
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

