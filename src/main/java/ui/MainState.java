package ui;

import static ui.OptionsListing.*;
import static ui.StateListing.*;

import schedulingapp.SchedulingApp;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class MainState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], MAINOPTIONS.length-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			tokenInt = Integer.MIN_VALUE;
		}
		//Process valid input
		switch(tokenInt) {
		case 0: 
			model.logout();
			controller.setState(LOGIN);
			break;
		case 1:
			controller.setState(SELECTPROJECT);
			break;
		case 2:
			controller.setState(NEWPROJECT);
			break;
		case 3:
			controller.setState(VIEWDEVS);
			break;
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showOptions(MAINOPTIONS);
	}

}
