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
	