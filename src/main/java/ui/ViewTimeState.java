package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;
import java.util.Calendar;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class ViewTimeState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		if(tokens.length != 1) {
			view.showError(E_INVALID_INPUT);
			return;
		}
		Calendar date = Utils.validateDate(tokens[0]);
		if(date == null) {
			view.showError(E_INVALID_DATE);
			return;
		}
		try {
			view.showMessage(String.format("You have %.1f hours registered on that date", model.getHoursOnActivity(date)));
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_VIEW_TIME);
	}

}
