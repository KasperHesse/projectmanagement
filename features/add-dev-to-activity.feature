Feature: Add developer to activity
    Description: A developer is added to an activity
    Actor: Project Manager, Developer
#
#Scenario: Project manager successfully adds developer to an activity
#    Given that the user is the project manager of project "xyz"
#    And that an activity named "research" exists under the current project
#    When they add the developer with initials "abcd" to the activity
#    Then the developer with initials "abcd" is added to the activity
#
#Scenario: Project manager receives an error message if they try to add a developer to an activity they already work on
#    Given that the user is the project manager of project "xyz"
#    And that an activity named "research" exists under the current project
#    And the the developer with initials "abcd" is working on the activity
#    When they add the developer with initials "abcd" to the activity
#    Then the error message "This developer is already working on this activity" is given.
#
#Scenario: A developer cannot add other developers to an activity
#    Given that the user is a developer of project "xyz"
#    And that an activity named "research" exists under the current activity
#    When they add the developer with initials "abcd" to the activity
#    Then the error message "Developers cannot add other developers to activities" is given.