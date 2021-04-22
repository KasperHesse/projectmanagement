Feature: Edit project
Description: User edits a project
Actors: Developer

Scenario: User adds themselves as project manager to a project with no project manager
	Given that a project exists with project number "012345"
	And no project manager is bound to the current project
	When the user "EPR" adds themselves as project manager to the project
	Then the current user is added as project manager to the current project
	
	
Scenario: User changes Start time of project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user changes the start time by 2 weeks for the current project
	Then the start time of the current project is changed by 2 weeks
	
	
Scenario: User changes End time of project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user changes the end time by 2 weeks for the current project
	Then the end time of the current project is changed by 2 weeks
	
	
Scenario: User removes an activity from a project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user removes the activity named "research" from the project "xyz"
	Then the activity named "research" is removed from the project "xyz"
	
Scenario: Add developer to project
	Given that the user with initials "abcd" is the project manager of project "xyz"
#	And a developer "EPR" exists in the current project
#	When the user adds the current developer to the current project
#	Then the current developer is added to the current project
#
#
#Scenario: User removes a developer from the project
#	Given that a project exists with project number "012345"
#	And the user is bound as a project manager to the current project
#	And a developer is bound to the current project
#	When the user removes a developer from the current project
#	Then the developer is removed from the current project
#
#
#Scenario: User changes hours budgeted for the project
#	Given that a project exists with project number "012345"
#	And the user is bound as a project manager to the current project
#	When the user changes hours budgeted by 100000 for the current project
#	Then the hours budgeted is changed by 100000 for the current project




