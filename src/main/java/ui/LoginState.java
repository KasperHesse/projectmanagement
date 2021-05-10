package ui;

import static ui.ControllerMessages.*;
import static ui.StateList.*;
//import static ui.ControllerStateEnum.sMAIN;

import schedulingapp.SchedulingApp;

public class LoginState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		//Invalid input checks
		if(tokens.length != 1 || tokens[0].length() != 4) {
			view.showError(ControllerMessages.E_ENTER_INITIALS);
		} else {
			//Login attempt
			if(!model.login(tokens[0].toLowerCase())) {
				view.showError(ControllerMessages.E_INVALID_INITIALS);
			} else {
				controller.setState(MAIN, true);
			}
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_WELCOME_MSG);
	}

}
