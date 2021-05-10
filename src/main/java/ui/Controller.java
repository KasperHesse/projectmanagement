package ui;

import schedulingapp.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.beans.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static dto.EventTypes.*;
import static ui.ControllerMessages.*;
import static ui.StateListing.*;

import dto.*;
//import dto.ProjectInfo;

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

	
	public Controller() {
		model = new SchedulingApp();
		view = new SchedulingAppCLI();
		initialSetup(); //Must perform setup before adding observer
		
		model.addObserver(this);
		this.state = LOGIN;
		stateStack = new Stack<>();

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
			if(line.equals("")) {
				continue;
			}
			matcher.reset(line);
			matches.clear();
			

			while(matcher.find()) {
				matches.add(matcher.group());
			}
			String[] tokens = matches.toArray(new String[0]);
						
			if(state == LOGIN) { //Must run before the if-statements below
				state.processInput(model, this, view, tokens);
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
				setState(MAIN);
				continue;
			}
			state.processInput(model, this, view, tokens);
		}
	}
	


	/**
	 * Sets the state of the controller, and calls any state-specific functionality that should always be performed.
	 * This version should onlyy be called when adding items to the state stack
	 * @param state The new state of the system
	 */
	void setState(ControllerState state) {
		setState(state, true);
	}
	
	/**
	 * Sets the state of the controller, and calls any state-specific functionality. 
	 * @param state The new state of the system
	 * @param shouldPush Whether the current state should be pushed onto the state stack
	 */
	void setState(ControllerState state, boolean shouldPush) {
		if(shouldPush) {stateStack.push(this.state); }
		this.state = state;
		state.enterState(model, this, view);
	}
	

	/**
	 * Shows a generic help message to the user
	 */
	private void showHelp() {
		view.showMessage(I_HELP_MSG);
	}

	/**
	 * Goes back to the previous state
	 */
	void goBack() {
		if(stateStack.isEmpty()) {
			view.showError("Cannot go further back");
			return;
		}
		setState(stateStack.pop(), false);
	}
	
	/**
	 * Verifies if the current user of the system is the project manager of the active project. Outputs an error message if this is not the case.
	 * @return True if they are the PM, false otherwise
	 */
	boolean ensureUserIsPM() {
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
	boolean projectHasPM() {
		return model.getProjectManager() != null;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(USER)) { //User has changed
			userChangedEvent(evt);
			
		} else if (evt.getPropertyName().equals(EventTypes.PROJECT)) { //Active project has changed
			projectChangedEvent(evt);
			
		} else if(evt.getPropertyName().equals(EventTypes.ACTIVITY)) { //Active activity has changed
			activeActivity = (ActivityInfo) evt.getNewValue();
			view.showMessage(String.format("Selected activity \"%s\"", activeActivity.getName()));
			
		} else if(evt.getPropertyName().equals(TIME)) { //Time registration changed
			if((Double) evt.getOldValue() == null) {
				view.showMessage(String.format("Registered %.1f hours on \"%s\"", (Double) evt.getNewValue(), activeActivity));
			} else {
				view.showMessage(String.format("Edited hours on \"%s\". Previous registration was %.1f, new registration is %.1f", activeActivity, (Double) evt.getOldValue(), (Double) evt.getNewValue()));
			}
	
		} else if(evt.getPropertyName().equals(ASSTDEV)) { //Assistant developer changed
			if(evt.getNewValue() != null) {
				view.showMessage(String.format("%s was added to the activity as an assistant developer", ((DeveloperInfo) evt.getNewValue()).getName()));
			} else {
				view.showMessage(String.format("%s was removed as an assistant developer", ((DeveloperInfo) evt.getOldValue()).getName()));
			}
		
		} else if (evt.getPropertyName().equals(BUDGET)) { //Hours budgetted
			view.showMessage(String.format("This activity now has %d hours budgetted", (int) evt.getNewValue()));
			activeActivity = model.getActiveActivity();
			
		} else if (evt.getPropertyName().equals(DEVLIST)) { //Developer list changed
			if(evt.getNewValue() != null) {
				view.showMessage(String.format("%s has been added as a developer", ((DeveloperInfo) evt.getNewValue()).getName()));
			} else {
				view.showMessage(String.format("%s has been removed as a developer", ((DeveloperInfo) evt.getOldValue()).getName()));
			}
			developerList = model.getProjectDeveloperList();
			
		} else if (evt.getPropertyName().equals(PROJMAN)) { //Project manager changed
			view.showMessage(String.format("The new project manager of this project is %s", ((DeveloperInfo) evt.getNewValue()).getName()));
			activeProject = model.getActiveProject();
		
		} else if (evt.getPropertyName().equals(STARTDATE)) { //Activity start date changed
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			String date = formatter.format(((Calendar) evt.getNewValue()).getTime());
			view.showMessage(String.format("Activity start date has been changed to %s", date));
			activityList = model.getActiveProjectActivities();
			activeActivity = model.getActiveActivity();
			
		} else if (evt.getPropertyName().equals(STOPDATE)) { //Activity stop date changed
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			String date = formatter.format(((Calendar) evt.getNewValue()).getTime());
			view.showMessage(String.format("Activity stop date has been changed to %s", date));
			activityList = model.getActiveProjectActivities();
			activeActivity = model.getActiveActivity();
		
		} else if (evt.getPropertyName().equals(PROJECTLIST)) { //Project list has been updated / new project
			view.showMessage(String.format("Project '%s' was added to the system", (ProjectInfo) evt.getNewValue()));
			
		} else if (evt.getPropertyName().equals(ACTIVITYLIST)) { //Activity was added under current project
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

