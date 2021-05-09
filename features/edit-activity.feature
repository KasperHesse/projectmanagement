Feature: Edit activity
Description: User edits an activity in a project
Actors: Developer

Scenario: User changes start time of activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a startdate "2021-04-06"
	When the user moves the start time by 2 weeks for the activity the "research"
	Then the start time of the activity "research" is "2021-04-20" 
	
Scenario: User changes End time of activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a stopdate "2021-05-06"
	When the user moves the end time by 2 weeks for the activity the "research"
	Then the end time of the activity "research" is "2021-05-20" 

Scenario: User removes a developer from an activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
    And that the developer with initials "efgh" is working on the activity	
	When the user removes the developer with initials "efgh" from the activity
	Then the developer with initials "efgh" is removed from the current activity


Scenario: User changes hours budgeted for activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the hours budgetted is 100 for the current activity
	When the user changes hours budgeted to 200 for the current activity
	Then the hours budgeted is 200 for the current activity
	
Scenario: User tries to removes a non-existent developer from an activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user removes the developer with initials "efgh" from the activity
	Then the error message "No developer with this name exists in this project" is given
	
Scenario: User sets new start time of activity to a date after stopdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a stopdate "2021-04-06"
	When the user changes startdate for the activity "research" to "2021-04-20"
	Then the error message "Given start date (2021 W16) must be before stop date (2021 W14)" is given
	
Scenario: User sets new stopdate of activity to a date before startdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a startdate "2021-04-20"
	When the user changes stopdate for the activity "research" to "2021-04-06"
	Then the error message "Given stop date (2021 W14) must be after start date (2021 W16)" is given
	
Scenario: User tries to change start time of activity with no current start time given
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user moves the start time by 2 weeks for the activity the "research"
	Then the error message "A date for a project must be given before changing" is given

Scenario: User tries to change end time of activity with no current end time given
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user moves the end time by 2 weeks for the activity the "research"
	Then the error message "A date for a project must be given before changing" is given	
	
Scenario: User changes start time of activity to a date after stopdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a startdate "2021-04-06"
	And the activity "research" has a stopdate "2021-04-10"
	When the user moves the start time by 2 weeks for the activity the "research"
	Then the error message "The startdate cannot be after the stopdate" is given
	
Scenario: User changes end time of activity to a date before creationdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a startdate "2021-01-06"
	And the activity "research" has a stopdate "2021-04-10"
	When the user moves the end time by -2 weeks for the activity the "research"
	Then the error message "The stopdate cannot be before the creationdate" is given

Scenario: User changes end time of activity to a date before startdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	And the activity "research" has a startdate "2031-04-06"
	And the activity "research" has a stopdate "2031-04-10"
	When the user moves the end time by -2 weeks for the activity the "research"
	Then the error message "The startdate cannot be after the stopdate" is given			