package schedulingapp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import schedulingapp.Activity;
import schedulingapp.ActivityHelper;
import schedulingapp.Developer;
import schedulingapp.DeveloperHelper;
import schedulingapp.Project;
import schedulingapp.ProjectHelper;
import schedulingapp.SchedulingApp;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import schedulingapp.Activity;
import schedulingapp.ActivityHelper;
import schedulingapp.Developer;
import schedulingapp.DeveloperHelper;
import schedulingapp.ProjectHelper;
import schedulingapp.SchedulingApp;

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
		Developer dev2 = devHelper.getDeveloper(initials);
	}

	@Given("{string} is not associated with the activity")
	public void is_not_associated_with_the_activity(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials);
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
//		schedulingApp.setCurrentUser(dev1);
		Developer dev2 = devHelper.getDeveloper(initials2);
		Project proj = projHelper.getProject();
		proj.setProjectManager(dev1);
		Activity act = actHelper.getActivity();		
		try {
			act.askForHelp(dev2, act);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
//	@When("{string} seeks assistance from {string}, who doesnt exist")
//	public void seeks_assistance_from_who_doesnt_exist(String initials1, String initials2) {
//		Developer dev1 = devHelper.getDeveloper(initials1);
//		Developer dev2 = new Developer("JONA","Jonthan");
//		Project proj = projHelper.getProject();
//		proj.setProjectManager(dev1);
//		Activity act = actHelper.getActivity();	
//		try {
//			act.askForHelp(dev2, act);
//		} catch (IllegalArgumentException e) {
//			errorMessageHolder.setErrorMessage(e.getMessage());
//		}
//	}
	
	@Then("{string} is now associated with activty")
	public void is_now_associated_with_activty(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials); 
		Activity act = actHelper.getActivity();
		assertTrue(act.isAssistingDeveloper(dev2));
	}
	
	@Given("{string} is not available")
	public void is_not_available_new(String initials) {
		Developer dev2 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act1 = actHelper.getActivity(proj,"activity1");
		Activity act2 = actHelper.getActivity(proj,"activity2");
		Activity act3 = actHelper.getActivity(proj,"activity3");
		Activity act4 = actHelper.getActivity(proj,"activity4");
		Activity act5 = actHelper.getActivity(proj,"activity5");
		Activity act6 = actHelper.getActivity(proj,"activity6");
		Activity act7 = actHelper.getActivity(proj,"activity7");
		Activity act8 = actHelper.getActivity(proj,"activity8");
		Activity act9 = actHelper.getActivity(proj,"activity9");
		Activity act10 = actHelper.getActivity(proj,"activity10");
		Activity act11 = actHelper.getActivity(proj,"activity11");

//		act1.addDeveloper(dev2);
//		act2.addDeveloper(dev2);
//		act3.addDeveloper(dev2);
//		act4.addDeveloper(dev2);
//		act5.addDeveloper(dev2);
//		act6.addDeveloper(dev2);
//		act7.addDeveloper(dev2);
//		act8.addDeveloper(dev2);
//		act9.addDeveloper(dev2);
//		act10.addDeveloper(dev2);
//		act11.addDeveloper(dev2);

		dev2.addDeveloperToActivity(act1);
		dev2.addDeveloperToActivity(act2);
		dev2.addDeveloperToActivity(act3);
		dev2.addDeveloperToActivity(act4);
		dev2.addDeveloperToActivity(act5);
		dev2.addDeveloperToActivity(act6);
		dev2.addDeveloperToActivity(act7);
		dev2.addDeveloperToActivity(act8);
		dev2.addDeveloperToActivity(act9);
		dev2.addDeveloperToActivity(act10);
		dev2.addDeveloperToActivity(act11);
		
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
	
//	@Given("{string} doesn’t exist in the system")
//	public void doesn_t_exist_in_the_system(String initials) {
//		Developer dev2 = new Developer(initials,"Jonathan");
//		//No need to create an object that does'nt exist
//	}
}






