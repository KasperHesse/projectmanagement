package schedulingapp;

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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;

public class editProjectSteps {
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	ActivityHelper actHelper;
	Calendar oldstartDate;
	Calendar oldstopDate;
	private Calendar initialStartDate;
	private Calendar initialStopDate;
	private Calendar initialCreationDate;

	public editProjectSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper,
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, ActivityHelper actHelper1) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper1;

	}

	// User adds themselves as project manager to a project with no project manager
	@Given("that a project exists with project number {string}")
	public void that_a_project_exists_with_project_number(String projectName) {
		Project project = projHelper.getProject(projectName);
		
		assertThat(schedulingApp.hasProjectNamed(projectName), is(true));

	}
	
	@Given("the project {string} has a startdate {string}")
	public void the_project_has_a_startdate(String projectName, String startDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		project.setStartDate(startDate);
		initialStartDate = project.getStartDate();
		
		
		
	}
	
	@When("the user changes the start time by {int} weeks for the project {string}")
	public void the_user_changes_the_start_time_by_weeks_for_the_project(Integer weeks, String projectName) {
			
		try {
			projHelper.getProject(projectName).changeStartDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the start time of the project {string} is {string}")
	public void the_start_time_of_the_project_is(String projectName, String startDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startCal = Calendar.getInstance();
		
		startCal.setTime(formatter.parse(startDate));
		
		assertEquals(project.getStartDate() , startCal);
		
	}
	
	@Given("no project manager is bound to the current project")
	public void no_project_manager_is_bound_to_the_current_project() {
		assertThat(projHelper.getProject().hasProjectManager(), is(false));

	}

	@When("the user {string} adds themselves as project manager to the project")
	public void the_user_adds_themselves_as_project_manager_to_the_project(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		projHelper.getProject().setProjectManager(dev);

	}

	@Then("the current user is added as project manager to the current project")
	public void the_current_user_is_added_as_project_manager_to_the_current_project() {
		assertThat(projHelper.getProject().hasProjectManager(), is(true));
	}

	@When("the user changes the start time by {int} weeks for the current project")
	public void the_user_changes_the_start_time_by_weeks_for_the_project(Integer weeks) {

		try {
			projHelper.getProject().changeStartDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}


	@When("the user changes the end time by {int} weeks for the current project")
	public void the_user_changes_the_end_time_by_weeks_for_the_current_project(Integer weeks) {
		
		try {
			projHelper.getProject().changeStopDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}


	@When("the user removes the activity named {string} from the project {string}")
	public void the_user_removes_the_activity_named_from_the_project(String projectName, String name) {
		try {
			projHelper.getProject(projectName).removeActivity(name);

		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}

	}

	@Then("the activity named {string} is removed from the project {string}")
	public void the_activity_named_is_removed_from_the_project(String projectName, String name) {
		assertThat(projHelper.getProject(projectName).hasActivityNamed(name), is(false));
	}

	@Given("a developer {string} exists")
	public void a_developer_exists(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		
		assertThat(schedulingApp.hasDevWithInitials(initials), is(true));
		
		
	}

	@When("the user adds the developer {string} to the current project")
	public void the_user_adds_the_developer_to_the_current_project(String initials) {

		Developer dev = devHelper.getDeveloper(initials);
		
		assertThat(devHelper.getDeveloper(initials).isAvailable(), is(true));
		
		try {
			projHelper.getProject().addDeveloper(dev);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the developer with initials {string} is added to the current project")
	public void the_developer_with_initials_is_added_to_the_current_project(String initials) {
		List<Developer> dList = projHelper.getProject().getDevelopers();

		assertThat(dList.stream().anyMatch(d -> d.getInitials().equals(initials)), is(true));
	}

	@Given("a developer {string} is bound to the current project")
	public void a_developer_is_bound_to_the_current_project(String initials) {
		Developer dev = devHelper.getDeveloper(initials);

		try {
			assertThat(projHelper.getProject().doesDeveloperExistInProject(dev), is(true));

		} catch (AssertionError e) {
			errorMessageHolder.setErrorMessage("That developer does not exist in the current project.");
		}
	}

	@When("the user removes the developer {string} from the current project")
	public void the_user_removes_the_developer_from_the_current_project(String initials) {
		Developer dev = devHelper.getDeveloper(initials);

		try {
			projHelper.getProject().removeDeveloper(dev);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the developer {string} is removed from the current project")
	public void the_developer_is_removed_from_the_current_project(String initials) {
		List<Developer> dList = projHelper.getProject().getDevelopers();
		assertThat(dList.stream().anyMatch(p -> p.getInitials().equals(initials)), is(false));
	}
	
	
	
	@Given("the project {string} has a enddate {string}")
	public void the_project_has_a_enddate(String projectName, String stopDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		project.setStopDate(stopDate);
		initialStopDate = project.getStopDate();
		
	}

	@When("the user changes the end time by {int} weeks for the project {string}")
	public void the_user_changes_the_end_time_by_weeks_for_the_project(Integer weeks, String projectName) {
		try {
			projHelper.getProject(projectName).changeStopDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		} 
	}

	@Then("the end time of the project {string} is {string}")
	public void the_end_time_of_the_project_is(String projectName, String stopDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar stopCal = Calendar.getInstance();
		
		stopCal.setTime(formatter.parse(stopDate));
		
		assertEquals(project.getStopDate() , stopCal);
	}
	
	
	
	@Given("the project {string} has no startdate")
	public void the_project_has_no_startdate(String projectName) {
		Project project = projHelper.getProject(projectName);
		
	}
	
	@Given("the project {string} has a startdate {string} and the creationDate is {string}")
	public void the_project_has_a_startdate_and_the_creation_date_is(String projectName, String startDate, String creationDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		project.setStartDate(startDate);
		project.setCreationDate(creationDate);
	}
	
	@Given("the project {string} has a startdate {string} and a stopdate {string}")
	public void the_project_has_a_startdate_and_a_stopdate(String projectName, String startDate, String stopDate) throws ParseException {
		Project project = projHelper.getProject(projectName);
		project.setStartDate(startDate);
		project.setStopDate(stopDate);
		initialStartDate = project.getStartDate();
		initialStopDate = project.getStopDate();
	}	
}