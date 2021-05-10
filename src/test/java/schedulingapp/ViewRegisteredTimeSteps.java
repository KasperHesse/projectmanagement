package schedulingapp;

import io.cucumber.java.en.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author Peter Ejlev, s183718
 *
 */
public class ViewRegisteredTimeSteps {
	SchedulingApp schedulingApp;
	Activity activity;
	Project project;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	TimeSheet timeSheet;
	
	public ViewRegisteredTimeSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, TimeSheet timeSheet) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
		this.timeSheet = timeSheet;
	}
	
	@Given("the user with the initials {string} has registered {int} hours on an activity on the date {string}")
	public void the_user_with_the_initials_has_registered_hours_on_an_activity_on_the_date(String initials, int hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		
		act.addDeveloper(dev);
		assertTrue(act.hasDeveloperWithInitials(initials));
		
		act.registerTime(dev, hours, date_cal);
	}
	
	@When("the user with the initials {string} views the {int} hours they spent on a that activity on the date {string}")
	public void the_user_with_the_initials_views_how_many_hours_they_spent_on_a_that_activity_on_the_date(String initials, int hours, String date) throws ParseException {
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		Developer dev = devHelper.getDeveloper(initials);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		act.registerTime(dev, hours, date_cal);
		
		try {
			
			act.viewTime(date_cal, dev);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("the user with the initials {string} that has registered {int} hours is shown their hours from the date {string}")
	public void the_user_with_the_initials_that_has_registered_hours_is_shown_their_hours_from_the_date(String initials, int hours, String date) throws ParseException {
		Developer dev = devHelper.getDeveloper(initials);
		Project proj = projHelper.getProject("project");
		Activity act = actHelper.getActivity(proj,"activity");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date_cal = Calendar.getInstance();
		date_cal.setTime(formatter.parse(date));
		
		assertEquals(act.viewTime(date_cal, dev), hours, 0.1);
	}
}


