package ui;

import schedulingapp.SchedulingApp;

import static ui.OptionsListing.*;
import static ui.StateListing.*;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class ActivityState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], ACTIVITYOPTIONS.length-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			tokenInt = Integer.MIN_VALUE;
		}
		switch(tokenInt) {
		case 0:
			controller.setState(REGISTERTIME);
			break;
		case 1:
			controller.setState(EDITREGISTEREDTIME);
			break;
		case 2:
			controller.setState(VIEWTIME);
			break;
		case 3:
			controller.setState(SEEKASSISTANCE);
			break;
		case 4:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(ADDDEVACT);
			break;
		case 5:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(REMOVEDEVACT);
			break;
		case 6:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(REMOVEASSTDEV);
			break;
		case 7:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(CHANGEBUDGETHOURS);
			break;
		case 8:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(CHANGESTARTWEEK);
			break;
		case 9:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(CHANGESTOPWEEK);
			break;
		}

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(controller.activeActivity.getInfoString());
		view.showOptions(ACTIVITYOPTIONS);
	}

}
