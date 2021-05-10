package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class RemoveDeveloperFromProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		//Project managers cannot remove themselves - must promote someone else to be PM
		int input = Utils.validateInteger(tokens[0], controller.developerList.size()-1);
		if(tokens.length != 1 || input == Integer.MIN_VALUE) {
			view.showError(E_INVALID_INPUT);
			return;
		}
		try {
			model.removeDeveloperFromProject(controller.developerList.get(input));
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		controller.developerList = model.getProjectDeveloperList();
		view.showMessage(I_REMOVE_DEV_PROJ);
		view.showOptions(controller.developerList);
	}

}
