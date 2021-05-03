Feature: view their registered time on an activity
	Description: 
    Actors: Project Manager, Developer
	
Scenario: User successfully views their registered time
	Given A user with the initials "PETE" is logged in
	And the user with the initials "PETE" has registered 8 hours on an activity on the date "2020-01-13"
	When the user with the initials "PETE" views the 8 hours they spent on a that activity on the date "2020-01-13"
	Then the user with the initials "PETE" that has registered 8 hours is shown their hours from the date "2020-01-13"
