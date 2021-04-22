package schedulingapp.unit_tests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.cucumber.java.en.*;
import schedulingapp.*;

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
	
	
	
	
	//    OLD STEPS
//	@When("the user creates the first project of year {int}")
//	public void the_user_creates_the_first_project_of_year(Integer year) {
//		schedulingApp.setCurrentDate(year, 0, 0);
//		schedulingApp.createProject("Test Project");
////		schedulingApp.getProjectByName("Test Project").setApp(schedulingApp);
//	}
//
//	@Then("a project with the project number {string} exists")
//	public void a_project_with_the_project_number_exists(String expectedProjectNumber) {
//	    assertTrue(expectedProjectNumber.equals(schedulingApp.getProjectByName("Test Project").getProjectNumber()));
//	}
//
//	@Then("{string} is a member of that project")
//	public void is_a_member_of_that_project(String string) {
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
//	}
}
