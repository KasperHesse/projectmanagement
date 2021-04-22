Feature: Create project
	Description: User creates a new project
	Actors: Developer

Scenario: User successfully creates a new project
	Given a user with initials "lomo" exists
	When the user creates a project
	Then a project with the project name "xyz" exists
	And "lomo" is a member of that project

#Scenario: User creates a new project and adds a project manager
#	Given a user with initials "lomo" exists
#	When a user creates a project named "xyz" and appoints "lomo" project manager
#	Then a project with the project name "xyz" exists
#	And "lomo" is the project manager of the project named "xyz"

#Scenario: User creates a new project with start/stop date
#	When a user creates a project with a start date and a stop date
#	Then a project with that project number exists
#	And the start date and stop date of the project is set
#	And the user is a member of the project