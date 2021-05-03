Feature: Register time
	Description: A developer wants to register time usage on project
	Actors: Developer
	
Scenario: User registers time on an activity successfully
    Given A user with the name "PETE" is logged in
    And "PETE" is associated with the activity he wants to register time usage on
    When "PETE" registers 24 hours on that activity on "2021-12-24"
    Then 24 hours is added to "PETE" time usage on that activity on "2021-12-24"
    
    Scenario: User registers time on an activity, but with too many hours
    Given A user with the name "PETE" is logged in
    And "PETE" is associated with the activity he wants to register time usage on
    When "PETE" registers 25 hours on that activity on "2021-12-24"
    Then the error message "You can't register more than 24 hours on one day" is given


Scenario: User cannot register time for an activity they are not added to
    Given A user with the name "PETE" is logged in
    And "PETE" user is not associated with the activity he wants to register time usage on
    When "PETE" registers 24 hours on that activity on "2021-12-24"
    Then the error message "You are not associated with chosen activity" is given

Scenario: User cannot register time on a project that is not active
	Given A user with the name "PETE" is logged in
	And "PETE" is associated with the activity he wants to register time usage on
	And an activity with start date "2021-12-24"  and end date "2021-12-31" exists under a project
	When "PETE" registers 24 hours on that activity on "2021-12-23"
	Then the error message "You cannot register time outside the active status dates" is given
	
Scenario: User registers time on an activity with a start- and stopdate succesfully
	Given A user with the name "PETE" is logged in
	And "PETE" is associated with the activity he wants to register time usage on, and the startdate is "2021-21-24" and the stopdate is "2021-12-31"
	When "PETE" registers 24 hours on that activity on "2021-12-25"
	Then 24 hours is added to "PETE" time usage on that activity on "2021-12-25"


#Given the constructor these scenraios are the same as the first one. I dont think theres any need for implementing them :) 
#Scenario: User registers time for vacation in the future
#    Given A user with the name "PETE" is logged in
#    And "PETE" is associated with the activity Holiday (which every developer is)
#    And "PETE" picks "24/12" for vacation
#    When "PETE" registers 8 hours
#    Then 8 hours of vacation is added to "24/12" under holiday
#
#Scenario: User registers time for an internal course in the future
#    Given A user with the name "PETE" is logged in
#    And "PETE" is associated with the activity Internal course (which every developer is)
#    And "PETE" picks "24/12" for internal course
#    When "PETE" registers 8 hours
#    Then 8 hours of Internal coursing is added to "24/12"