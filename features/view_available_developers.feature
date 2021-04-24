Feature: View available developers
	Description: User views a list of all available developers
	Actors: Project Manager, Developer

Scenario: Project Manager views a list of all developers on the project who are working on less than 10 activities
	Given a user with initials "lomo" exists
	And "lomo" is project manager for any project
	And "dev1" , "dev2" , "dev3" are all available developers
	When "lomo" views the list of available developers
	Then developers "dev1" , "dev2" , "dev3" are shown to the user

#Scenario: Developer is unable to view the list of available developers
#	Given a user with initials "lomo" exists
#	When "lomo" views the list of available developers
#	Then Error message "Only project managers can view available developers" is thrown