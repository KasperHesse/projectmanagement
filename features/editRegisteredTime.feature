<<<<<<< Updated upstream
Feature: edit the registered time for an activity
    Description: 
    Actors: Project Manager, Developer

Scenario: User successfully edits their registered time
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity
	When the user "PETE" changes their registered time with 3 hours
	Then their registered time changes with 3 hours
	
=======
Feature: edit their registered time on an activity
	Description: banan
    Actors: Project Manager, Developer
	
	Scenario: User successfully edits their registered time
		Given A user with the initials "PETE" is logged in
		And the user "PETE" is a developer under the activity "Research" under the project "Test"
		When the user "PETE" changes their registered time on the activity "Research" with 3 hours on the date "2020-01-13"
		Then 3 hours is edited to "PETE" time usage on that activity on "2021-01-13"
>>>>>>> Stashed changes
