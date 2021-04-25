Feature: edit the registered time for an activity
    Description: 
    Actors: Project Manager, Developer

Scenario: User successfully edits their registered time
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity
	When the user "PETE" changes their registered time with 3 hours
	Then their registered time changes with 3 hours
	