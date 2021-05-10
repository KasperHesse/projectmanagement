Feature: Seek Assistance
	Description: A developer seeks assistance
	Actors: Developer

Scenario: User seeks assistance successfully
    Given "PETE" is logged in
    And "JONA" exists in the system
    And "JONA" is not associated with the activity
    And "JONA" is available
    When "PETE" seeks assistance from "JONA"
    Then "JONA" is now associated with activty

Scenario: User cannot seek assistance from developer that is already a part of 10 activities
    Given "PETE" is logged in 
    And "JONA" exists in the system
    And "JONA" is not associated with the activity
    And "JONA" is not available
    When "PETE" seeks assistance from "JONA"
    Then the error message "The requested developer is associated with max amount of activities" is given
    
Scenario: User cannot seek assistance from a developer already on the activity
    Given "PETE" is logged in
	 And "JONA" exists in the system
    And "JONA" is associated with the activity
    When "PETE" seeks assistance from "JONA"
    Then the error message "The requested developer is already associated with the activity" is given

Scenario: User removes assisting developer
    Given "PETE" is logged in
    And "JONA" is assisting developer on activity "abc"
    When "PETE" removes "JONA" as assisting developer from activity "abc"
    Then "JONA" is no longer associated with activity "abc"

Scenario: User tries to remove non-existent assisting developer
    Given "PETE" is logged in
    When "PETE" removes "JONA" as assisting developer from activity "abc"
    Then the error message "No assisting developer with this name exists in this project" is given

Scenario: User cannot seek from an non-existent colleague 
    Given "PETE" is logged in
    When "PETE" seeks assistance on activity "abc" from "JONA" , who doesnt exist
    Then the error message "The requested developer is not in the system" is given