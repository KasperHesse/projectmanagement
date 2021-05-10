package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class RemoveAssistantDeveloperState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], controller.developerList.size()-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		try {
			model.removeAssistantDeveloper(controller.developerList.get(tokenInt));
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		controller.developerList = model.getAssistantDeveloperList();
		if(controller.developerList.isEmpty()) {
			view.showMessage(I_NO_DEVELOPERS_REMOVE);
			controller.goBack();
			return;
		}
		view.showMessage(I_REMOVE_ASST_DEV);
		view.showOptions(controller.developerList);
	}

}
