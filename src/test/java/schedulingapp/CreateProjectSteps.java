package schedulingapp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import io.cucumber.java.en.*;
import schedulingapp.*;

/**
 * 
 * @author Emil Mortensen, s204483
 *
 */
public class CreateProjectSteps {
	DeveloperHelper devHelper;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	SchedulingApp schedulingApp;
	ErrorMessageHolder errorMessageHolder;
	
	public CreateProjectSteps(DeveloperHelper devHelper, ProjectHelper projHelper, SchedulingApp schedulingApp,
			ActivityHelper actHelper, ErrorMessageHolder errorMessageHolder) {
		this.devHelper = devHelper;
		this.projHelper = projHelper;
		this.schedulingApp = schedulingApp;
		this.actHelper = actHelper;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@Given("a user with initials {string} exists")
	public void a_user_with_initials_exists(String initials) {
	    Developer testDev = devHelper.getDeveloper(initials);
	    schedulingApp.setCurrentUser(testDev);
	}

	@When("the user creates a project with name {string}")
	public void the_user_creates_a_project_with_name(String projectName) {
		schedulingApp.createProject(projectName);
	}

	@Then("the project named {string} has a correct, unique project number")
	public void the_project_named_has_a_correct_unique_project_number(String projectName) {
		GregorianCalendar currentDate = new GregorianCalendar();
		String projectNumber = schedulingApp.getProjectByName(projectName).getProjectNumber();
		int firstTwoDigits = Integer.parseInt(projectNumber)/10000;
		int lastDigits = Integer.parseInt(projectNumber)%10000;
		
		assertTrue(firstTwoDigits == currentDate.get(Calendar.YEAR)%100);
		assertTrue(lastDigits == schedulingApp.getAmountOfProjectsCreatedYear(currentDate.get(Calendar.YEAR))-1);
		assertTrue(schedulingApp.hasProjectWithNumber(projectNumber));
	}
	
	@When("the user creates a project with name {string} and appoints {string} project manager")
	public void the_user_creates_a_project_with_name_and_appoints_project_manager(String projectName, String initials) {
		Developer testDev = schedulingApp.getDeveloperByInitials(initials);
	    schedulingApp.createProject(projectName, testDev);
	}

	@Then("a project with the project name {string} exists")
	public void a_project_with_the_project_name_exists(String projectName) {
		assertTrue(schedulingApp.hasProjectNamed(projectName));
	}

	@Then("{string} is the project manager of the project named {string}")
	public void is_the_project_manager_of_the_project_named(String initials, String projectName) {
		Developer testDev = schedulingApp.getDeveloperByInitials(initials);
		Project testProject = schedulingApp.getProjectByName(projectName);
		assertTrue(testProject.isProjectManager(testDev));
		assertTrue(testDev.equals(testProject.getProjectManager()));
	}
	
	@When("the user creates a project with name {string}, start date {string} and stop date {string}")
	public void the_user_creates_a_project_with_name_start_date_and_stop_date(String projectName, String startDate, String stopDate) throws Exception {
		schedulingApp.createProject(projectName, startDate, stopDate);
	}

	@Then("the start date of the project {string} is {string}")
	public void the_start_date_of_the_project_is(String projectName, String startDate) throws Exception {
		Project project = schedulingApp.getProjectByName(projectName);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));

		assertThat(startCal.compareTo(project.getStartDate()), is(0));
	}

	@Then("the stop date of the project {string} is {string}")
	public void the_stop_date_of_the_project_is(String projectName, String stopDate) throws Exception {
		Project project = schedulingApp.getProjectByName(projectName);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		Calendar stopCal = Calendar.getInstance();
		stopCal.setTime(formatter.parse(stopDate));

		assertThat(stopCal.compareTo(project.getStopDate()), is(0));
	}
	
	@Then("{string} is a member of the project {string}")
	public void is_a_member_of_the_project(String initials, String projectName) {
		assertTrue(schedulingApp.getProjectByName(projectName).getDevelopers().contains(devHelper.getDeveloper(initials)));
	}
}
