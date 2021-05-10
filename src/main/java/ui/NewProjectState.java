package ui;

import static ui.ControllerMessages.*;
import static ui.OptionsListing.*;
import static ui.StateList.*;

import java.util.*;

import dto.*;
import schedulingapp.SchedulingApp;

public class NewProjectState implements ControllerState {

	@Override
	public void processInput(SchedulingApp model, Controller controller, SchedulingAppCLI view, String[] tokens) {
		//Parse string for tokens, find the token after "=" and use as the key
		String name = "";
		String start = "";
		String stop = "";
		String pm = "";
		for(String token : tokens) {
			try {
				if(token.contains("name") && name.equals("")) {
					name = Utils.findValueAfterKey(token, "name");
				}
				if(token.contains("start") && start.equals("")) {
					start = Utils.findValueAfterKey(token, "start");
				}
				if(token.contains("stop") && stop.equals("")) {
					stop = Utils.findValueAfterKey(token, "stop");
				}
				if(token.contains("pm") && pm.equals("")) {
					pm = Utils.findValueAfterKey(token, "pm");
				}
			} catch (IllegalArgumentException e) { //One of the token strings did not match the required format
				view.showError("Error on '" + token + "'. " + E_KEYVAL_BADFORMAT);
				return;
			} catch (NoSuchElementException e) { //Key was not present in the string
				view.showError("Error on '" + token + "'. " + E_KEY_NOT_PRESENT);
			}
		}
		
		if(name.equals("")) {
			view.showError(E_NEW_PROJ_MUST_HAVE_NAME);
			return;
		}
		
		//Check if the PM (if given) is valid
		DeveloperInfo projMan = null;
		if(!pm.equals("")) {
			projMan = model.getDeveloperWithInitials(pm);
			if(projMan == null || !projMan.getAvailableFlag())  {
				view.showError(E_DEV_MAX_PROJECTS);
				return;
			}
		}
		
		//Check if start/stop dates are valid
		Calendar startDate = null;
		Calendar stopDate = null;
		if(!start.equals("")) {
			startDate = Utils.validateDate(start);
			if(startDate == null) {
				view.showError("Error on start date. " + E_INVALID_DATE);
			}
		}
		if(!stop.equals("")) {
			stopDate = Utils.validateDate(stop);
			if(stopDate == null) {
				view.showError("Error on stop date. " + E_INVALID_DATE);
			}
		}
		if(startDate != null && stopDate != null && !startDate.before(stopDate)) {
			view.showError(E_DATES_INVALID);
			return;
		}
		try {
			model.addNewProject(name, startDate, stopDate, projMan);
			controller.goBack();
		} catch (Exception e) {
			view.showError(e.getMessage());
		}
	}

	@Override
	public void enterState(SchedulingApp model, Controller controller, SchedulingAppCLI view) {
		view.showMessage(I_NEW_PROJECT);
	}

}
