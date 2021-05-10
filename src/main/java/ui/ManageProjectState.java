package ui;

import schedulingapp.SchedulingApp;
import static ui.OptionsListing.*;
import static ui.StateListing.*;

/**
 * @author Kasper Hesse, s183735
 *
 */
public class ManageProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], MANAGEPROJECTOPTIONS.length-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
		}
		switch(tokenInt) {
		case 0:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(ADDDEVPROJ);
			break;
		case 1:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(REMOVEDEVPROJ);
			break;
		case 2:
			if(controller.projectHasPM() && !controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(CHANGEPROJMAN);
			break;
		case 3:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			view.showError("Not yet implemented");
			view.showOptions(MANAGEPROJECTOPTIONS);
			break;
		case 4:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(NEWACTIVITY);
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showOptions(MANAGEPROJECTOPTIONS);
	}

}
