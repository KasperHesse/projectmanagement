package ui;

import schedulingapp.SchedulingApp;
import static ui.StateListing.*;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class SelectActivityState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], controller.activityList.size()-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			tokenInt = Integer.MIN_VALUE;
		}
		model.setActiveActivity(controller.activityList.get(tokenInt));
		controller.activeActivity = model.getActiveActivity();
		controller.setState(ACTIVITY);

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showOptions(controller.activityList);
	}

}
