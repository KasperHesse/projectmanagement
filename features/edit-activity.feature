Feature: Edit activity
Description: User edits an activity in a project
Actors: Developer

Scenario: User changes start time of activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user moves the start time by 2 weeks for the current activity
	Then the start time of the current activity is moved
	
Scenario: User changes End time of activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user moves the end time by 2 weeks for the current activity
	Then the end time of the current activity is moved

Scenario: User removes a developer from an activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
    And that the developer with initials "efgh" is working on the activity	
	When the user removes the developer with initials "efgh" from the activity
	Then the developer with initials "efgh" is removed from the current activity


Scenario: User changes hours budgeted for activity
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user changes hours budgeted by 100 for the current activity
	Then the hours budgeted is changed by 100 for the current activity
	