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
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;


	
public class viewRegisteredTimeSteps {

	SchedulingApp schedulingApp;
	Activity activity;
	Project project;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	TimeSheet timeSheet;
	
	public viewRegisteredTimeSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, TimeSheet timeSheet) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
		this.timeSheet = timeSheet;
	}
	
	
	@Given("the user with the initials {string} has registered {int} hours on an activity on the date {string}")
	public void the_user_with_the_initials_has_registered_hours_on_an_activity_on_the_date(String initials, Integer hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		
		timeSheet.registerTime(dev, hours, date_cal);
		activity.registerTime(dev, hours, date_cal);
	}

	@When("the user with the initials {String} views how many hours they spent on a that activity on the date {String}")
	public void the_user_views_how_much_time_they_spent_on_a_that_activity(String initials, String date) throws ParseException {
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		Developer dev = devHelper.getDeveloper(initials);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		
		try {
			act.viewTime(date_cal, dev);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the user with the initials {String} that has registered {int} hours is shown their hours from the date {String}")
	public void the_registered_hours_for_is_shown_to_the_user_from_the_date(String initials, int hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		
		assertEquals(timeSheet.viewTime(date_cal, dev), hours);

	    
	}
}
