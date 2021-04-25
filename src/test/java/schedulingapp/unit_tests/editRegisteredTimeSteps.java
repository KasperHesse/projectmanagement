package schedulingapp.unit_tests;

import io.cucumber.java.en.*;
import java.util.*;
import schedulingapp.ActivityHelper;
import schedulingapp.Developer;
import schedulingapp.DeveloperHelper;
import schedulingapp.ProjectHelper;
import schedulingapp.SchedulingApp;
import schedulingapp.Activity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class editRegisteredTimeSteps {
	
	SchedulingApp schedulingApp;
	Activity activity;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	DeveloperHelper devHelper;
	ErrorMessageHolder errorMessageHolder;
	
	public editRegisteredTimeSteps(SchedulingApp schedulingApp, ProjectHelper projHelper, DeveloperHelper devHelper, 
			ErrorMessageHolder errorMessageHolder, ActivityHelper actHelper) {
		this.schedulingApp = schedulingApp;
		this.projHelper = projHelper;
		this.devHelper = devHelper;
		this.errorMessageHolder = errorMessageHolder;
		this.actHelper = actHelper;
	}
	
	@Given("A user with the initials {string} is logged in")
	public void a_user_with_the_initials_is_logged_in(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		schedulingApp.setCurrentUser(dev);
	}

	@Given("the user {string} is a developer under the activity")
	public void the_user_is_a_developer_under_the_activity(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		activity.addDeveloper(dev);
	    assertThat(Activity.hasDeveloperWithInitials(initials), is(true));
	}

	@When("the user {string} changes their registered time with {int} hours")
	public void the_user_changes_their_registered_time_with_hours(String string, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("their registered time changes with {int} hours")
	public void their_registered_time_changes_with_hours(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
