Feature: edit their registered time on an activity
	Description: A developer wants to edit their registered time usage on an activity
    Actors: Project Manager, Developer
	
Scenario: User successfully edits their registered time
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity "Research" under the project "Test"
	And "PETE" has registered 4 hours on the date "2020-01-13"
	When the user "PETE" changes their registered time on the activity "Research" with 3 hours on the date "2020-01-13"
	Then 3 hours is edited to "PETE" time usage on that activity on "2021-01-13"
	
Scenario: User tries to edit time but edited time exceeds daily time limit
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity "Research" under the project "Test"
	And "PETE" has registered 4 hours on the date "2020-01-13"
	When the user "PETE" changes their registered time on the activity "Research" with 22 hours on the date "2020-01-13"
	Then the error message "You can't register more than 24 hours on one day" is shown

Scenario: User inputs date not in between start and stop date of activity
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity "activity" under the project "Test"
	And the activity "activity" has a startdate "2021-12-24"
	And the activity "activity" has a stopdate "2021-12-31"
	And "PETE" has registered 4 hours on the date "2021-12-30"
	And the end date is set to "2021-12-26" afterwards
	When the user "PETE" changes their registered time on the activity "activity" with 22 hours on the date "2021-12-30"
	Then the error message "You cannot register time outside the active status dates" is shown

Scenario: User cannot edit time for an activity they are not added to
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity "Research" under the project "Test"
	And "PETE" has registered 4 hours on the date "2020-01-13"
	And "PETE" is afterwards removed from the activity "Research"
	When the user "PETE" changes their registered time on the activity "Research" with 2 hours on the date "2020-01-13"
	Then the error message "You are not associated with chosen activity" is shown
	
Scenario: User cannot edit time for another developer
	Given A user with the initials "PETE" is logged in
	And the user "PETE" is a developer under the activity "Research" under the project "Test"
	And the user "JONA" is a developer under the activity "Research" under the project "Test"
	And "JONA" has registered 4 hours on the date "2020-01-13"
	When the user "PETE" changes registered time of "JONA" on the activity "Research" with 2 hours on the date "2020-01-13"
	Then the error message "You can't edit other developers registered time" is shown