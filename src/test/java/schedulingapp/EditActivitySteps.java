package schedulingapp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Jonathan Michelsen, s204437
 *
 */
public class EditActivitySteps {
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	ActivityHelper actHelper;

	public EditActivitySteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper,
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, ActivityHelper actHelper1) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper1;

	}

	@Given("the activity {string} has a startdate {string}")
	public void the_activity_has_a_startdate(String name, String startDate) throws ParseException {
		Project project = projHelper.getProject();

		Activity activity = actHelper.getActivity(project, name);
		activity.setStartDate(startDate);

	}

	@When("the user moves the start time by {int} weeks for the activity the {string}")
	public void the_user_moves_the_start_time_by_weeks_for_the_activity_the(Integer weeks, String name) {
		Project project = projHelper.getProject();
		try {
			actHelper.getActivity(project, name).changeStartDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the start time of the activity {string} is {string}")
	public void the_start_time_of_the_activity_is(String name, String startDate) throws ParseException {
		Project project = projHelper.getProject();
		Activity activity = actHelper.getActivity(project, name);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startCal = Calendar.getInstance();

		startCal.setTime(formatter.parse(startDate));

		assertEquals(activity.getStartDate(), startCal);
	}

	@Given("the activity {string} has a stopdate {string}")
	public void the_activity_has_a_stopdate(String name, String stopDate) throws ParseException {
		Project project = projHelper.getProject();

		Activity activity = actHelper.getActivity(project, name);
		activity.setStopDate(stopDate);
	}

	@When("the user moves the end time by {int} weeks for the activity the {string}")
	public void the_user_moves_the_end_time_by_weeks_for_the_activity_the(Integer weeks, String name) {
		Project project = projHelper.getProject();
		try {
			actHelper.getActivity(project, name).changeStopDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the end time of the activity {string} is {string}")
	public void the_end_time_of_the_activity_is(String name, String stopDate) throws ParseException {
		Project project = projHelper.getProject();
		Activity activity = actHelper.getActivity(project, name);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar stopCal = Calendar.getInstance();

		stopCal.setTime(formatter.parse(stopDate));

		assertEquals(activity.getStopDate(), stopCal);
	}
	
	@When("the user removes the developer with initials {string} from the activity")
	public void the_user_removes_the_developer_from_the_activity(String initials) {
		Developer d = devHelper.getDeveloper(initials);
		try {
			actHelper.getActivity().removeDeveloper(d);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the developer with initials {string} is removed from the current activity")
	public void the_developer_with_initials_is_removed_from_the_current_activity(String initials) {
		List<Developer> dList = actHelper.getActivity().getDevelopers();
		assertThat(dList.stream().anyMatch(d -> d.getInitials().equals(initials)), is(false));
	}

	@Given("the hours budgetted is {int} for the current activity")
	public void the_hours_budgetted_is_for_the_current_activity(Integer hoursBudgetted) {
		actHelper.getActivity().setHoursBudgeted(hoursBudgetted);
	}

	@When("the user changes hours budgeted to {int} for the current activity")
	public void the_user_changes_hours_budgeted_to_for_the_current_activity(Integer newHours) {
		actHelper.getActivity().setHoursBudgeted(newHours);
	}

	@Then("the hours budgeted is {int} for the current activity")
	public void the_hours_budgeted_is_for_the_current_activity(Integer hoursBudgetted) {
		assertThat(actHelper.getActivity().getHoursBudgeted() == hoursBudgetted, is(true));
	}
	
	@When("the user changes startdate for the activity {string} to {string}")
	public void the_user_changes_startdate_for_the_activity_to(String name, String startDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startDate));
		
		Project project = projHelper.getProject();
		Activity activity = actHelper.getActivity(project, name);
		
		try {			
			activity.setStartDate(startCal);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("the user changes stopdate for the activity {string} to {string}")
	public void the_user_changes_stopdate_for_the_activity_to(String name, String stopDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar stopCal = Calendar.getInstance();
		stopCal.setTime(formatter.parse(stopDate));
		
		Project project = projHelper.getProject();
		Activity activity = actHelper.getActivity(project, name);
		
		try {			
			activity.setStopDate(stopCal);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	

}
