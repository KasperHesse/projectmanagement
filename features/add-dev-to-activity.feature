Feature: Add developer to activity
    Description: A developer is added to an activity
    Actor: Project Manager, Developer

Scenario: Project manager successfully adds developer to an activity
    Given that the user with initials "abcd" is the project manager of project "xyz"
    And that an activity named "research" exists under the current project
    When they add the developer with initials "efgh" to the activity
    Then the developer with initials "efgh" is added to the activity

Scenario: Project manager receives an error message if they try to add a developer to an activity they already work on
    Given that the user with initials "abcd" is the project manager of project "xyz"
    And that an activity named "research" exists under the current project
    And that the developer with initials "efgh" is working on the activity
    When they add the developer with initials "efgh" to the activity
    Then the error message "This developer is already working on this activity" is shown

Scenario: A developer cannot add other developers to an activity
    Given that the user with initials "abcd" is the project manager of project "xyz"
    And that an activity named "research" exists under the current project
    And that the current user has initials "dcba"
    When they add the developer with initials "efgh" to the activity
    Then the error message "Developers cannot add other developers to activities" is shown