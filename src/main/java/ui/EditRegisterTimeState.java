package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;

import java.util.Calendar;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class EditRegisterTimeState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		if(tokens.length != 2) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		Double hours = Utils.validateDouble(tokens[1], -24, 24);
		
		if(date == null) {
			view.showError(ControllerMessages.E_INVALID_DATE);
			return;
		} else if (hours == Integer.MIN_VALUE) {
			view.showError(E_INVALID_HOURS);
			return;
		}
		try {
			model.editTimeOnActivity(date, hours);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(ControllerMessages.I_EDIT_REGISTERED_TIME);
	}

}
