package ui;

import static ui.ControllerMessages.*;
import static ui.OptionsListing.*;
import static ui.StateList.*;

import schedulingapp.SchedulingApp;

public class ProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], PROJECTOPTIONS.length - 1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		switch(tokenInt) {
		case 0:
			if(controller.activityList.size() == 0) {
				view.showError(E_NO_ACTIVITIES);
				break;
			}
			controller.setState(SELECTACTIVITY);
			break;
		case 1:
			controller.setState(MANAGEPROJECT);
			break;
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		// TODO Auto-generated method stub

	}

}
