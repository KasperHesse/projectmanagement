Feature: Edit project
Description: User edits a project
Actors: Developer

Scenario: User adds themselves as project manager to a project with no project manager
	Given that a project exists with an auto-generated project number 
	And no project manager is bound to the current project
	When the user "EPR" adds themselves as project manager to the project
	Then the current user is added as project manager to the current project
	
Scenario: User adds themselves as project manager to a project with a current project manager
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user "EPR" adds themselves as project manager to the project
	Then the error message "Only the current PM can change the project manager" is given	
	
Scenario: User changes Start time of project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a startdate "2021-04-06"
	When the user changes the start time by 2 weeks for the project "xyz"
	Then the start time of the project "xyz" is "2021-04-20"
	
	
Scenario: User changes End time of project
	Given that the user with initials "abcd" is the project manager of project "xyz"	
	Given the project "xyz" has a enddate "2021-05-06"
	When the user changes the end time by 2 weeks for the project "xyz"
	Then the end time of the project "xyz" is "2021-05-20"
	
	
Scenario: User removes an activity from a project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And that an activity named "research" exists under the current project
	When the user removes the activity named "research" from the project "xyz"
	Then the activity named "research" is removed from the project "xyz"
	
Scenario: Add developer to project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And a developer "EPR" exists
	When the user adds the developer "EPR" to the current project
	Then the developer with initials "EPR" is added to the current project


Scenario: User removes a developer from the project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	And a developer "EPR" is bound to the current project
	When the user removes the developer "EPR" from the current project
	Then the developer "EPR" is removed from the current project

Scenario: User changes start time which is null
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has no startdate 
	When the user changes the start time by 2 weeks for the project "xyz"
	Then the error message "A date for a project must be given before changing" is given
	
Scenario: User changes stop time which is null
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user changes the end time by 2 weeks for the project "xyz"
	Then the error message "A date for a project must be given before changing" is given

Scenario: User changes start time to before the creationdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a startdate "2021-04-20" and the creationDate is "2021-04-19"
	When the user changes the start time by -2 weeks for the project "xyz"
	Then the error message "The startdate cannot be before the creationdate" is given
	
Scenario: User changes stop time to before the creationdate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a stopdate "2021-04-20" and the creationDate is "2021-04-19"
	When the user changes the end time by -2 weeks for the project "xyz"
	Then the error message "The stopdate cannot be before the creationdate" is given	
	
Scenario: User changes start time to after stopDate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a startdate "2021-04-20" and a stopdate "2021-04-22"
	When the user changes the start time by 1 weeks for the project "xyz"
	Then the error message "The startdate cannot be after the stopdate" is given

Scenario: User changes start time to after stopDate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a startdate "2021-04-20" and a stopdate "2021-04-22"
	When the user changes the start time by 1 weeks for the project "xyz"
	Then the error message "The startdate cannot be after the stopdate" is given

Scenario: User changes stop time to after startDate
	Given that the user with initials "abcd" is the project manager of project "xyz"
	Given the project "xyz" has a startdate "2021-04-20" and a stopdate "2021-04-22"
	When the user changes the end time by -1 weeks for the project "xyz"
	Then the error message "The startdate cannot be after the stopdate" is given
	
Scenario: Project manager tries to remove himself from the project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user removes the developer "abcd" from the current project						
	Then the error message "Project managers cannot remove themselves from a project. Promote a new PM first" is given
	
Scenario: User tries to remove non-existent developer from the project
	Given that the user with initials "abcd" is the project manager of project "xyz"
	When the user removes the developer "efgh" from the current project						
	Then the error message "No developer with these initials exists in this project" is given	