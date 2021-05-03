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

#Scenario: User cannot seek from an non existent colleague 
#    Given "PETE" is logged in
#    And "JONA" doesn’t exist in the system
#    When "PETE" seeks assistance from "JONA", who doesnt exist
#    Then the error message "The requested developer is not in the system" is given