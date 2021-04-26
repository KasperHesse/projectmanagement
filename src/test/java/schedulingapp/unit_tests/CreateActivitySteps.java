package schedulingapp.unit_tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;



import io.cucumber.java.en.*;
import schedulingapp.*;

public class CreateActivitySteps {
	
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	
	public CreateActivitySteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
	}
	
	@Given("that the user with initials {string} is the project manager of project {string}")
	public void that_the_user_with_initials_is_the_project_manager_of_project(String initials, String projectName) {
		Project project = projHelper.getProject(projectName);
		Developer dev = devHelper.getDeveloper(initials);
		project.setProjectManager(dev);
		schedulingApp.setCurrentUser(dev);
	}

	@When("they create an activity named {string} under the current project")
	public void they_create_an_activity_named_under_the_current_project(String name) {
		try {
			projHelper.getProject().createActivity(name);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("an activity named {string} is created under the current project")
	public void an_activity_named_is_created_under_the_current_project(String name) {
	    assertThat(projHelper.getProject().hasActivityNamed(name), is(true));
	}

	@When("they create an activity named {string} under the project {string}")
	public void they_create_an_activity_named_under_the_project(String activityName, String projectName) {
		try {
			schedulingApp.getProjectByName(projectName).createActivity(activityName);
		} catch (NoSuchElementException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the error message {string} is shown")
	public void the_error_message_is_shown(String errorMsg) {
		assertThat(errorMessageHolder.getErrorMessage(), is(errorMsg));
	}

	
	@When("they create an activity named {string} starting on {string} and finishing on {string}")
	public void they_create_an_activity_named_starting_on_and_finishing_on(String name, String startDate, String stopDate) throws Exception {
		projHelper.getProject().createActivity(name, startDate, stopDate);
	}

	@Then("an activity named {string} exists under the current project with start date {string} and stop date {string}")
	public void an_activity_named_exists_under_the_current_project_with_start_date_and_end_date(String name, String startDate, String stopDate) throws Exception {
		Activity a = projHelper.getProject().getActivityByName(name);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar startCal = Calendar.getInstance();
		Calendar stopCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));
		stopCal.setTime(formatter.parse(stopDate));

		assertThat(startCal.compareTo(a.getStartDate()), is(0));
		assertThat(stopCal.compareTo(a.getStopDate()), is(0));
	}

	@Given("that an activity named {string} exists under the current project")
	public void that_an_activity_named_exists_under_the_current_project(String name) {
		actHelper.getActivity(projHelper.getProject(), name);
		
	}

	@Given("that the user with initials {string} is a developer of project {string}")
	public void that_the_user_with_initials_is_a_developer_of_project(String initials, String projectName) {
		projHelper.getProject(projectName);
		Developer dev = devHelper.getDeveloper(initials);
		schedulingApp.setCurrentUser(dev);
	}
	



}
