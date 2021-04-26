package schedulingapp.unit_tests;

import io.cucumber.java.en.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import schedulingapp.ActivityHelper;
import schedulingapp.Developer;
import schedulingapp.DeveloperHelper;
import schedulingapp.Project;
import schedulingapp.ProjectHelper;
import schedulingapp.SchedulingApp;
import schedulingapp.Activity;
import schedulingapp.TimeSheet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;

public class editRegisteredTimeSteps {

	SchedulingApp schedulingApp;
	Activity activity;
	Project project;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	TimeSheet timeSheet;
	
	public editRegisteredTimeSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, TimeSheet timeSheet) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
		this.timeSheet = timeSheet;
	}
	
	
	@Given("A user with the initials {string} is logged in")
	public void a_user_with_the_initials_is_logged_in(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		schedulingApp.setCurrentUser(dev);
		Project proj = projHelper.getProject("project");
		proj.setProjectManager(dev);
	}

	@Given("the user {string} is a developer under the activity {string} under the project {string}") 
	public void the_user_is_a_developer_under_the_activity(String initials, String activityName, String projectName) {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		act.addDeveloper(dev);
		assertTrue(act.hasDeveloperWithInitials(initials));
	   
	}

	@When("the user {string} changes their registered time on the activity {string} with {int} hours on the date {string}")
	public void the_user_changes_their_registered_time_with_hours(String initials, String activityName, Integer hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
		try {
			act.editTime(dev, hours, timeRegisterDate);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("{int} hours is edited to {string} time usage on that activity on {string}")
	public void hours_is_edited_to_time_usage_on_that_activity_on(Integer hours, String initials, String date) throws Exception{
		Developer dev1 = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
		int int1 = act.viewTime(timeRegisterDate, dev1);
		act.editTime(dev1, hours, timeRegisterDate);
		int int2 = act.viewTime(timeRegisterDate, dev1);
		
		assertEquals(int1+hours, int2);
		
	}
	
	
}
