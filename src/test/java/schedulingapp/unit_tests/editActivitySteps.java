package schedulingapp.unit_tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

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

public class editActivitySteps {
	SchedulingApp schedulingApp;
	ProjectHelper projHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	ActivityHelper actHelper;

	public editActivitySteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper,
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper, ActivityHelper actHelper1) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper1;

	}

	@When("the user moves the start time by {int} weeks for the current activity")
	public void the_user_moves_the_start_time_by_for_the_current_activity(Integer weeks) {
		try {
			actHelper.getActivity().addWeeksToStartDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the start time of the current activity is moved")
	public void the_start_time_of_the_current_activity_is_moved_by_two_weeks() {
		try {
			assertThat(actHelper.getActivity().hasStartDateChanged(), is(true));

		} catch (AssertionError e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@When("the user moves the end time by {int} weeks for the current activity")
	public void the_user_moves_the_end_time_by_weeks_for_the_current_activity(Integer weeks) {
		try {
			actHelper.getActivity().addWeeksToStopDate(weeks);
		} catch (IllegalArgumentException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the end time of the current activity is moved")
	public void the_end_time_of_the_current_activity_is_moved_by_two_weeks() {
		try {
			assertThat(actHelper.getActivity().hasStopDateChanged(), is(true));

		} catch (AssertionError e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
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

	@When("the user changes hours budgeted by {int} for the current activity")
	public void the_user_changes_hours_budgeted_by_for_the_current_activity(Integer hours) {
		actHelper.getActivity().addHours(hours);
	}

	@Then("the hours budgeted is changed by {int} for the current activity")
	public void the_hours_budgeted_is_changed_by_for_the_current_activity(Integer hours) {
		assertThat(actHelper.getActivity().hoursHasChanged(hours), is(true));
	}
}
