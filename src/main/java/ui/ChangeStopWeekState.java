package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;

import java.util.Calendar;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class ChangeStopWeekState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		Calendar date = Utils.validateDate(tokens[0]);
		if(tokens.length != 1 || date == null) {
			view.showError(E_INVALID_DATE);
		}
		try {
			model.setActivityStopDate(date);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_CHANGE_STOP_WEEK);
	}

}
