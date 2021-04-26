package ui;

import schedulingapp.*;


public class SchedulingAppCLI {
	
	public SchedulingAppCLI() {
		
	}
	
	/**
	 * Shows an error message generated by the controller. Should *not* be used for error messages taken from exceptions
	 * @param errorMsg
	 */
	void showError(String errorMsg) {
		String[] tokens = errorMsg.split("\n");
		System.out.printf("ERROR: %s\n", tokens[0]);
		for(int i = 1; i<tokens.length; i++) {
			System.out.printf("\t%s\n", tokens[i]);
		}
	}

	public void showMessage(String message) {
		System.out.printf("INFO: %s\n", message);
		
	}

	public void showOptions(String[] options) {
		System.out.println("Please select one of the below options: ");
		for(int i=0; i<options.length; i++) {
			System.out.printf("[%d] %s\n", i, options[i]);
		}
	}
	
	
}
