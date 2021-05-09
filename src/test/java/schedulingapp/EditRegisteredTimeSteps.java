package schedulingapp;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;

public class EditRegisteredTimeSteps {

	SchedulingApp schedulingApp;
	Activity activity;
	Project project;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	TimeSheet timeSheet;
	
	public EditRegisteredTimeSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
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

	@Given("{string} has registered {int} hours on the date {string}")
	public void has_registered_hours_on_the_date(String initials, Integer hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar timeRegisterDate = Calendar.getInstance();
		
		schedulingApp.setCurrentUser(dev);
		timeRegisterDate.setTime(formatter.parse(date));
		act.registerTime(dev, hours, timeRegisterDate);
	}
	
	@When("the user {string} changes their registered time on the activity {string} with {int} hours on the date {string}")
	public void the_user_changes_their_registered_time_with_hours(String initials, String activityName, Integer hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
//		act.registerTime(dev, hours, timeRegisterDate);
		schedulingApp.setCurrentUser(dev);
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
		
		act.registerTime(dev1, hours, timeRegisterDate);
		
		double int1 = act.viewTime(timeRegisterDate, dev1);
		act.editTime(dev1, hours, timeRegisterDate);
		double int2 = act.viewTime(timeRegisterDate, dev1);
		
		assertEquals(int1+hours, int2, 0.1);	
	}
	
//	@Given("{string} user is not associated with the activity he wants to edit time usage on")
//	public void user_is_not_associated_with_the_activity_he_wants_to_edit_time_usage_on(String initials) {
//		Project proj = projHelper.getProject("project");
//		Activity act = actHelper.getActivity(proj,"activity");
//		assertFalse(act.hasDeveloperWithInitials(initials));
//	}
	
	@Given("the end date is set to {string} afterwards")
	public void the_end_date_is_set_to_afterwards(String endDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(formatter.parse(endDate));
		
		Project project = projHelper.getProject();
		Activity activity = actHelper.getActivity(project, "activity");
		activity.setStopDate(endCal);
		
	}
	
	@Given("{string} is afterwards removed from the activity {string}")
	public void is_afterwards_removed_from_the_activity(String initials, String actName) {
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj, "activity");
		Developer dev = devHelper.getDeveloper(initials);
		act.removeDeveloper(dev);
		
		assertFalse(act.hasDeveloperWithInitials(initials));
	}
	
	@When("the user {string} changes registered time of {string} on the activity {string} with {int} hours on the date {string}")
	public void the_user_changes_registered_time_of_on_the_activity_with_hours_on_the_date(String initials1, String initials2, String actName, Integer hours, String date) throws ParseException {
		Developer loggedUser = devHelper.getDeveloper(initials1);
		Developer dev = devHelper.getDeveloper(initials2);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar timeRegisterDate = Calendar.getInstance();
		timeRegisterDate.setTime(formatter.parse(date));
		
//		act.registerTime(dev, hours, timeRegisterDate);
		schedulingApp.setCurrentUser(loggedUser);
		try {
			act.editTime(dev, hours, timeRegisterDate);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
}
