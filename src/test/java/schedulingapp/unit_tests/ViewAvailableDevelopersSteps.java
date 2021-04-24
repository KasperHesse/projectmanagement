package schedulingapp.unit_tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
	
	@Given("{string} , {string} , {string} are all available developers")
	public void are_all_available_developers(String dev1Initials, String dev2Initials, String dev3Initials) {
		Developer dev1 = devHelper.getDeveloper(dev1Initials);
		Developer dev2 = devHelper.getDeveloper(dev2Initials);
		Developer dev3 = devHelper.getDeveloper(dev3Initials);
		
		assertTrue(dev1.isAvailable());
		assertTrue(dev2.isAvailable());
		assertTrue(dev3.isAvailable());
	}

	@When("{string} views the list of available developers")
	public void views_the_list_of_available_developers(String initials) {
	    availableDevelopers = schedulingApp.getAvailableDevelopers();
	}

	@Then("developers {string} , {string} , {string} are shown to the user")
	public void developers_are_shown_to_the_user(String dev1Initials, String dev2Initials, String dev3Initials) {
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev1Initials)));
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev2Initials)));
		assertTrue(availableDevelopers.contains(devHelper.getDeveloper(dev3Initials)));
	}
	
	
	
	
	
}
