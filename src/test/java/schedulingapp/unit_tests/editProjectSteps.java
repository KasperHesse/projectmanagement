package schedulingapp.unit_tests;

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
import static org.hamcrest.CoreMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

public class editProjectSteps {
	SchedulingApp schedulingApp;
	private ProjectHelper projHelper;
	private DeveloperHelper devHelper;
	private ErrorMessageHolder errorMessageHolder;
	private ActivityHelper actHelper;
	
	
	public editProjectSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
		
	}
	
	//User adds themselves as project manager to a project with no project manager
	@Given("that a project exists with project number {string}")
	public void that_a_project_exists_with_project_number(String projectName) {
		Project project = projHelper.getProject(projectName);
	    assertThat(schedulingApp.hasProjectNamed(projectName), is(true));
	    
	  
	}
	
	@Given("no project manager is bound to the current project")
	public void no_project_manager_is_bound_to_the_current_project() {
		assertThat(projHelper.getProject().hasProjectManager(), is(false));
		
	}
	
	@When("the user {string} adds themselves as project manager to the project")
	public void the_user_adds_themselves_as_project_manager_to_the_project(String initials) {
		Project project = projHelper.getProject();
		Developer dev = devHelper.getDeveloper(initials);
		project.setProjectManager(dev);
		schedulingApp.setCurrentUser(dev);
	}
	
	@Then("the current user is added as project manager to the current project")
	public void the_current_user_is_added_as_project_manager_to_the_current_project() {
		assertThat(projHelper.getProject().hasProjectManager(), is(true));
	}
		
	
	//User changes Start time of project
	@When("the user changes the start time by {int} weeks for the current project")
	public void the_user_changes_the_start_time_by_weeks_for_the_project(Integer weeks) {
		Project project = projHelper.getProject();
		
		try {
			project.addWeeksToStartDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}	
		
	}
	
	@Then("the start time of the current project is changed by {int} weeks")
	public void the_start_time_of_the_current_project_is_changed_by_weeks(Integer weeks) {
		
		try {
			assertThat(projHelper.getProject().hasStartDateChanged(), is(true));
		
		} catch (AssertionError e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
		
	}
	//The method is not done. And observer pattern needs to be made in order to see changes.
	
	//User changes End time of project
	@When("the user changes the end time by {int} weeks for the current project")
	public void the_user_changes_the_end_time_by_weeks_for_the_current_project(Integer weeks) {
		Project project = projHelper.getProject();
		
		try {
			project.addWeeksToStopDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("the end time of the current project is changed by {int} weeks")
	public void the_end_time_of_the_current_project_is_changed_by_weeks(Integer weeks) {
		
		try {
			assertThat(projHelper.getProject().hasStopDateChanged(), is(true));
		
		} catch (AssertionError e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	
	//User removes an activity from a project
			
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
	
	
//	@Given("a developer {string} exists in the current project")
//	public void a_developer_exists_in_the_current_project(String initials) {
//	    try {
//	    	assertThat(projHelper.getProject().hasDeveloperWithInistials
//	    }
//	    
//	    //Research if developer is bound to an activity in a project. Use contains.. 
//	}

	
}
		
		

		
	
	
	

	




	
















