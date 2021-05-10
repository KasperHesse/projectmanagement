package ui;

import schedulingapp.SchedulingApp;

import java.util.Calendar;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class RegisterTimeState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], 0.5, 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_HOURS);
			return;
		}
		try {
			model.registerTimeOnActivity(date, hours);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(ControllerMessages.I_REGISTER_TIME);
	}

}
