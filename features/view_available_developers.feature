Feature: View available developers
	Description: User views a list of all available developers
	Actors: Project Manager, Developer

Scenario: Project Manager views a list of all developers on the project who are working on less than 10 activities
	Given a user with initials "lomo" exists
	And "lomo" is project manager for any project
	And "dev1" , "dev2" are all available developers
	When "lomo" views the list of available developers
	Then developers "dev1" , "dev2" are shown to the user

Scenario: Developer is unable to view the list of available developers
	Given a user with initials "lomo" exists
	When "lomo" views the list of available developers
	Then Error message "Only project managers can view available developers" is thrown
	
Scenario: Project Manager views the list of available developers with only 1 developer
	Given a user with initials "lomo" exists
	And "lomo" is project manager for any project
	And "dev1" is the only available developers
	When "lomo" views the list of available developers
	Then developer "dev1" is shown to the user
	
Scenario: Project Manager views a list of all developers when a developer is not available
	Given a user with initials "lomo" exists
	And "lomo" is project manager for any project
	And developer "dev1" is unavailable while "dev2" is available
	When "lomo" views the list of available developers
	Then developer "dev2" is shown to the user