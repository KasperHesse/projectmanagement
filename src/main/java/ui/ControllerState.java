package ui;

import schedulingapp.SchedulingApp;
/**
 * @author Kasper Hesse, s183735
 *
 */
public interface ControllerState {
	
	/**
	 * Called whenever this state is active and new input has arrived from the user
	 * @param tokens The user input
	 */
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens);
	
	/**
	 * Called whenever this state is activated by the controller
	 * @param model
	 * @param controller
	 */
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view);

}
