package schedulingapp.unit_tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;

import io.cucumber.java.en.*;
import schedulingapp.*;


public class ViewAvailableDevelopersSteps {
	DeveloperHelper devHelper;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	SchedulingApp schedulingApp;
	ErrorMessageHolder errorMessageHolder;
	private List<Developer> availableDevelopers;
	
	public ViewAvailableDevelopersSteps(DeveloperHelper devHelper, ProjectHelper projHelper, SchedulingApp schedulingApp,
			ActivityHelper actHelper, ErrorMessageHolder errorMessageHolder) {
		this.devHelper = devHelper;
		this.projHelper = projHelper;
		this.schedulingApp = schedulingApp;
		this.actHelper = actHelper;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@Given("{string} is project manager for any project")
	public void is_project_manager_for_any_project(String initials) {
		Developer testDev = devHelper.getDeveloper(initials);
		Project testProj = projHelper.getProject("Test Project");
		testProj.setProjectManager(testDev);
	}
	
	@Given("{string} , {string} are all available developers")
	public void are_all_available_developers(String dev1Initials, String dev2Initials) {
		Developer dev1 = devHelper.getDeveloper(dev1Initials);
		Developer dev2 = devHelper.getDeveloper(dev2Initials);
		
		assertTrue(dev1.isAvailable());
		assertTrue(dev2.isAvailable());
	}


	@When("{string} views the list of available developers")
	public void views_the_list_of_available_developers(String initials) throws Exception {
		try {
			availableDevelopers = schedulingApp.viewAvailableDevelopers();
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	    
	}

	@Then("developers {string} , {string} are shown to the user")
	public void developers_are_shown_to_the_user(String dev1Initials, String dev2Initials) {
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev1Initials)));
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev2Initials)));
	} 
	
	
	@Then("Error message {string} is thrown")
	public void error_message_is_thrown(String errorMsg) {
		assertThat(errorMessageHolder.getErrorMessage(), is(errorMsg));
	}
	
	@Given("{string} is the only available developers")
	public void is_the_only_available_developers(String dev1Initials) {
		Developer dev1 = devHelper.getDeveloper(dev1Initials);
		
		assertTrue(dev1.isAvailable());
	}

	@Then("developer {string} is shown to the user")
	public void developer_is_shown_to_the_user(String dev1Initials) {
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev1Initials)));
	}
	
	@Given("developer {string} is unavailable while {string} is available")
	public void developer_is_unavailable_while_is_available(String dev1Initials, String dev2Initials) {
		Developer dev1 = devHelper.getDeveloper(dev1Initials);
		Project proj = projHelper.getProject();
		proj.addDeveloper(dev1);
		
		for (int i = 0; i < 11; i++) {
			actHelper.getActivity(proj, "" + i).addDeveloper(dev1);
		}
		
		Developer dev2 = devHelper.getDeveloper(dev2Initials);
		
		assertFalse(dev1.isAvailable());
		assertTrue(dev2.isAvailable());
	}
	
	
}
