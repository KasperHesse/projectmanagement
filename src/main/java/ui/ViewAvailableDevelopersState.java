package ui;

import static ui.ControllerMessages.*;

import schedulingapp.SchedulingApp;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class ViewAvailableDevelopersState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		//No input is processed in this state

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_VIEW_DEVS);
		controller.developerList = model.getAvailableDevelopers();
		view.showOptions(controller.developerList, "");
		controller.goBack();
	}

}
