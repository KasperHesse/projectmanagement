package schedulingapp.unit_tests;

import java.util.*;
import schedulingapp.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import io.cucumber.java.en.*;


public class AddDeveloperToActivitySteps {
	
	DeveloperHelper devHelper;
	ProjectHelper projHelper;
	ActivityHelper actHelper;
	SchedulingApp schedulingApp;
	ErrorMessageHolder errorMessageHolder;
	
	public AddDeveloperToActivitySteps(DeveloperHelper devHelper, ProjectHelper projHelper, SchedulingApp schedulingApp,
			ActivityHelper actHelper, ErrorMessageHolder errorMessageHolder) {
		this.devHelper = devHelper;
		this.projHelper = projHelper;
		this.schedulingApp = schedulingApp;
		this.actHelper = actHelper;
		this.errorMessageHolder = errorMessageHolder;
	}

	@When("they add the developer with initials {string} to the activity")
	public void they_add_the_developer_with_initials_to_the_activity(String initials) {
		Developer dev = devHelper.getDeveloper(initials);
		try {
			actHelper.getActivity().addDeveloper(dev);
		} catch (Exception e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the developer with initials {string} is added to the activity")
	public void the_developer_with_initials_is_added_to_the_activity(String initials) {
		List<Developer> dList = actHelper.getActivity().getDevelopers();
		assertThat(dList.stream().anyMatch(d -> d.getInitials().equals(initials)), is(true));
	}
	
	@Given("that the developer with initials {string} is working on the activity")
	public void that_the_developer_with_initials_is_working_on_the_activity(String initials) {
		Developer d = devHelper.getDeveloper(initials);
		actHelper.getActivity().addDeveloper(d);
	}
	
	@Given("that the current user has initials {string}")
	public void that_the_current_user_has_initials(String initials) {
	    Developer d = devHelper.getDeveloper(initials);
	    schedulingApp.setCurrentUser(d);
	}
}
