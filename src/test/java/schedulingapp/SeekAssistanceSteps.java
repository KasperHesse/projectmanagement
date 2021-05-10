package schedulingapp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



/**
 * 
 * @author Emil Pontoppidan, s204441
 *
 */
public class SeekAssistanceSteps {
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	
	public SeekAssistanceSteps (SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, ActivityHelper actHelper, ErrorMessageHolder errorMessageHolder) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper; 
		this.actHelper = actHelper;
		this.errorMessageHolder = errorMessageHolder;
		
	}
	@Given("{string} is logged in")
	public void is_logged_in(String initials) {
		Developer dev1 = devHelper.getDeveloper(initials);
		schedulingApp.setCurrentUser(dev1);
		Project proj = projHelper.getProject("project");
		proj.setProjectManager(dev1);
	}

	@Given("{string} exists in the system")
	public void exists_in_the_system(String initials) {
		devHelper.getDeveloper(initials);
	}

	@Given("{string} is not associated with the activity")
	public void is_not_associated_with_the_activity(String initials) {
		devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		assertFalse(act.hasDeveloperWithInitials(initials));
	}

	@Given("{string} is available")
	public void is_associated_with_less_than_activities(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials);
		assertTrue(dev2.isAvailable());
	}

	
	@When("{string} seeks assistance from {string}")
	public void seeks_assistance_from(String initials1, String initials2) {
		Developer dev1 = devHelper.getDeveloper(initials1);
		Developer dev2 = devHelper.getDeveloper(initials2);
		Project proj = projHelper.getProject();
		proj.setProjectManager(dev1);
		Activity act = actHelper.getActivity();		
		try {
			act.askForHelp(dev2);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("{string} seeks assistance on activity {string} from {string} , who doesnt exist")
	public void seeks_assistance_on_activity_from_who_doesnt_exist(String initials1, String initials2, String actName) {
		Activity act = actHelper.getActivity(projHelper.getProject(), actName);		
		try {
			act.askForHelp(new Developer(initials2, "name"));
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	
	@Then("{string} is now associated with activty")
	public void is_now_associated_with_activty(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials); 
		Activity act = actHelper.getActivity();
		assertTrue(act.isAssistingDeveloper(dev2));
	}
	
	@Given("{string} is not available")
	public void is_not_available_new(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject();
		
		proj.addDeveloper(dev2);
		
		for (int i = 0; i < 13; i++) {
			actHelper.getActivity(proj, "" + i).addDeveloper(dev2);
		}
		
		assertFalse(dev2.isAvailable());
	}

	@Then("the error message {string} is given")
	public void the_error_message_is_given_new(String errorMsg) {
		assertThat(errorMessageHolder.getErrorMessage(), is(errorMsg));
	}
	
	@Given("{string} is associated with the activity")
	public void is_associated_with_the_activity(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		act.addDeveloper(dev2);
		assertTrue(act.hasDeveloperWithInitials(initials));
	}
	
	
	@Given("{string} is assisting developer on activity {string}")
	public void is_assisting_developer_on_activity(String initials, String actName) {
		Developer dev = devHelper.getDeveloper(initials);
		Activity act = actHelper.getActivity(projHelper.getProject(), actName);
		act.askForHelp(dev);
		assertTrue(act.isAssistingDeveloper(dev));
	}

	@When("{string} removes {string} as assisting developer from activity {string}")
	public void removes_as_assisting_developer_from_activity(String initials1, String initials2, String actName) {
		Activity act = actHelper.getActivity(projHelper.getProject(), actName);
	    Developer dev2 = devHelper.getDeveloper(initials2);
	    
	    try {
	    	act.removeAssistingDeveloper(dev2);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	

	@Then("{string} is no longer associated with activity {string}")
	public void is_no_longer_associated_with_activity(String initials, String actName) {
		Developer dev = devHelper.getDeveloper(initials);
		Activity act = actHelper.getActivity(projHelper.getProject(),actName);
		
		assertFalse(act.getAssistingDeveloperList().stream().anyMatch(d -> d.equals(dev)));
	}
	
	
	
}






