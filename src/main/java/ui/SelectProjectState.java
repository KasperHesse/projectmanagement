package ui;

import static ui.StateListing.*;

import schedulingapp.SchedulingApp;
/**
 * @author Kasper Hesse, s183735
 *
 */
public class SelectProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], controller.projectList.size()-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
			return;
		}
		model.setActiveProject(controller.projectList.get(tokenInt));
		controller.activityList = model.getActiveProjectActivities();
		controller.setState(PROJECT);
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		controller.projectList = model.getVisisbleProjects();
		view.showOptions(controller.projectList);

	}

}
