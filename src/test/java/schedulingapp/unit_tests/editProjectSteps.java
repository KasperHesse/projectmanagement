package schedulingapp.unit_tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
		
	
	
	
	
	}




	
















