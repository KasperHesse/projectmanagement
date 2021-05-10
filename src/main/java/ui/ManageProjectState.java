package ui;

import schedulingapp.SchedulingApp;
import static ui.ControllerMessages.*;
import static ui.ControllerStateEnum.sADDDEVPROJ;
import static ui.ControllerStateEnum.sCHANGEPROJMAN;
import static ui.ControllerStateEnum.sGETTIMEREPORT;
import static ui.ControllerStateEnum.sNEWACTIVITY;
import static ui.ControllerStateEnum.sREMOVEDEVPROJ;
import static ui.OptionsListing.*;
import static ui.StateList.*;


public class ManageProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		int tokenInt = Utils.validateInteger(tokens[0], MANAGEPROJECTOPTIONS.length-1);
		if(tokens.length != 1 || tokenInt == Integer.MIN_VALUE) {
			view.showError(ControllerMessages.E_INVALID_INPUT);
		}
		switch(tokenInt) {
		case 0:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			controller.setState(ADDDEVPROJ);
			break;
		case 1:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			setState(REMOVEDEVPROJ);
			break;
		case 2:
			if(controller.projectHasPM() && !controller.ensureUserIsPM()) {
				break;
			}
			setState(CHANGEPROJMAN);
			break;
		case 3:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			setState(GETTIMEREPORT);
			view.showError("Not yet implemented");
			controller.goBack();
			break;
		case 4:
			if(!controller.ensureUserIsPM()) {
				break;
			}
			setState(NEWACTIVITY);
		}

	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showOptions(MANAGEPROJECTOPTIONS);
	}

}
