package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class ChangeBudgetHoursState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int input = Utils.validateInteger(tokens[0], Integer.MAX_VALUE);
		if(tokens.length != 1 || input == Integer.MIN_VALUE) {
			view.showError(E_INVALID_BUDGET);
			return;
		}
		try {
			model.setActivityTimeBudget(input);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_CHANGE_BUDGET_HOURS);
		view.showMessage(String.format("This activity currently has %d hours budgeted", model.getActivityTimeBudget()));
	}
}
