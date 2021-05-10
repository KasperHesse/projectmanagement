package ui;

import static ui.ControllerMessages.*;
import static ui.OptionsListing.*;
import static ui.StateList.*;

import schedulingapp.SchedulingApp;

public class ViewDevsState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_VIEW_DEVS);
		controller.developerList = model.getAvailableDevelopers();
		view.showOptions(controller.developerList, "");
		controller.goBack();
	}

}
