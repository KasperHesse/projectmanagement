package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;

import dto.DeveloperInfo;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class SeekAssistanceState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		DeveloperInfo d = model.getDeveloperWithInitials(tokens[0]);
		if(tokens.length != 1 || tokens[0].length() != 4 || d == null) {
			view.showError(E_INVALID_INITIALS);
			return;
		}
		try {
			model.addAssistantDeveloper(d);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_SEEK_ASSISTANCE);
	}

}
