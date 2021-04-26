package schedulingapp.unit_tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import schedulingapp.TimeSheet;

public class RegisterTimeSteps {
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	TimeSheet timeSheet;
	
	public RegisterTimeSteps (TimeSheet timesheet, SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, ActivityHelper actHelper, ErrorMessageHolder errorMessageHolder) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper; 
		this.actHelper = actHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.timeSheet = timeSheet; 
	}
	
	@Given("A user with the name {string} is logged in")
	public void a_user_with_the_name_is_logged_in(String initials) {
		Developer dev1 = devHelper.getDeveloper(initials);
		schedulingApp.setCurrentUser(dev1);
		Project proj = projHelper.getProject("project");
		proj.setProjectManager(dev1);
	}

	@Given("{string} is associated with the activity he wants to register time usage on")
	public void is_associated_with_the_activity_he_wants_to_register_time_usage_on(String initials) {
		Developer dev1 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		act.addDeveloper(dev1);
		assertTrue(act.hasDeveloperWithInitials(initials));
	}
	
	@When("{string} registers {int} hours on that activity on {string}")
	public void registers_hours_on_that_activity_on(String initials, Integer hours, String date) throws Exception{
		Developer dev1 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
		try {
			act.registerTime(dev1, hours, timeRegisterDate);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("{int} hours is added to {string} time usage on that activity on {string}")
	public void hours_is_added_to_time_usage_on_that_activity_on(Integer hours, String initials, String date) throws Exception{
		Developer dev1 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
		assertEquals(act.viewTime(timeRegisterDate, dev1),act.viewTime(timeRegisterDate, dev1));
	}
	
	@Given("{string} user is not associated with the activity he wants to register time usage on")
	public void user_is_not_associated_with_the_activity_he_wants_to_register_time_usage_on(String initials) {
		Developer dev1 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		assertFalse(act.hasDeveloperWithInitials(initials));
	}

	@Given("an activity with start date {string}  and end date {string} exists under a project")
	public void an_activity_with_start_date_and_end_date_exists_under_a_project(String startDate, String stopDate)throws Exception {
	    Project proj = projHelper.getProject("project");
	    Activity act = actHelper.getActivity(proj,"activity");
	}
}











