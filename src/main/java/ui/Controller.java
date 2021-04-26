package ui;

import schedulingapp.*;
import static ui.ControllerState.*;
import java.util.*;
import java.beans.*;

/**
 * The controller in the MVC-based user interface.
 * @author kaspe
 *
 */
public class Controller implements PropertyChangeListener {
	SchedulingApp model;
	SchedulingAppCLI view;
	ControllerState state;
	
	private String[] LOGINOPTIONS = new String[] {"Logout", 
			"Show projects", 
			"View free developers"};
	
	private String[] PROJECTOPTIONS = new String[] {"Register time", 
			"Edit registered time", 
			"Seek assistance", 
			"View registered time", 
			"Remove assisant developer (PM)", 
			"Go back"};
	
	public Controller() {
		model = new SchedulingApp();
		view = new SchedulingAppCLI();
		model.addObserver(this);
		this.state = sIDLE;
		initialSetup();
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
		model.addDeveloper(new Developer("kahe", "Kasper Hesse"));
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
			System.out.print("\n> ");
			String input = consoleParser.nextLine();
			switch(state) {
			case sIDLE: processInputIdle(input); break;
			case sLOGIN: processInputLogin(input); break;
//			case sPROJECT: processInputProject(input); break;
//			case sACTIVITY: processInputActivity(input); break;
			default: throw new Exception("An unknown error occured");
			}
			
		}
	}
	
	/**
	 * Used to process input when the system is in the IDLE state
	 * @param input The input string obtained from the command line
	 */
	private void processInputIdle(String input) {
		String[] tokens = input.split(" ");
		if(tokens.length != 1 || tokens[0].length() != 4) {
			view.showError("Please enter your initials (4 characters)");
		} else {
			if(!model.login(tokens[0].toLowerCase())) {
				view.showError("No user with those initials is registered");
			} else {
				setState(sLOGIN);
			}
		}
	}
	
	/**
	 * Used to process input when the system is in the LOGIN state (logged in, but no actions performed yet)
	 * @param input The input string obtained from the command line
	 */
	private void processInputLogin(String input) {
		String[] tokens = input.split(" ");
		int tokenInt;
	
		//Input validation
		if(tokens.length != 1) {
			view.showError("Unable to understand input. Please type one of the numbers from the list\n" +
					"To view the list of options again, type \"help\"");
		}
		if(tokens[0].equalsIgnoreCase("help")) {
			view.showOptions(LOGINOPTIONS);
			return;
		}
		try {
			tokenInt = Integer.parseInt(tokens[0]);
		} catch(NumberFormatException e) {
			view.showError("Unable to understand input. Please type one of the numbers from the list\n" +
					"To view the list of options again, type \"help\"");
			return;
		}
		//Process valid input
		switch(tokenInt) {
		case 0: 
			model.logout();
			setState(sIDLE);
			break;
		case 1:
			setState(sPROJECT);
			break;
		case 2:
//			this.state = sVIEWDEVS;
//			view.showOptions(model.getAvailableDevelopers());
			view.showError("Not yet implemented");
			break;
		default:
			view.showError("Your choice did not correspond to a valid option. Please type one of the numbers from the list\n" +
					"To view the list of options again, type \"help\"");
			break;

		}
	}
	
	/**
	 * Sets the state of the controller, and calls any state-specific functionality that should always be performed.
	 * @param state
	 */
	private void setState(ControllerState state) {
		this.state = state;
		switch(state) {
		case sLOGIN:
			view.showOptions(LOGINOPTIONS);
			break;
		case sIDLE:
			showWelcomeMessage();
			break;
		case sPROJECT:
			view.showOptions(PROJECTOPTIONS);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("user")) {
			userChangedEvent(evt);
		}
	}
	
	/**
	 * Processes an event where the current user has changed.
	 * @param evt The event that was fired
	 */
	public void userChangedEvent(PropertyChangeEvent evt) {
		if(evt.getNewValue() == null) {
			view.showMessage("Logged out");
		} else {
			view.showMessage(String.format("Logged in. Welcome, %s.", ((Developer) evt.getNewValue()).getName()));
		}
	}
}
