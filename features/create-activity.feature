Feature: Create activity
    Description: A new activity is created under a project, sometimes with additional information specified
    Actors: Project Manager, Developer

Scenario: User successfully creates an activity
    Given that the user with initials "abcd" is the project manager of project "xyz"
    When they create an activity named "research" under the current project
    Then an activity named "research" is created under the current project

Scenario: A user cannot create an activity without a project to place it under
   Given that the user with initials "abcd" is the project manager of project "xyz"
   When they create an activity named "research" under the project "zyx"
   Then the error message "This project does not exist" is shown

Scenario: User creates an activity with predefined start/stop date
   Given that the user with initials "abcd" is the project manager of project "xyz"
   When they create an activity named "research" starting on "2021-04-01" and finishing on "2021-05-01"
   Then an activity named "research" exists under the current project with start date "2021-04-01" and stop date "2021-05-01"

Scenario: User cannot create activities with duplicate names
   Given that the user with initials "abcd" is the project manager of project "xyz"
   And that an activity named "research" exists under the current project
   When they create an activity named "research" under the current project
   Then the error message "Activities must have a unique name" is shown

Scenario: A developer cannot create new activities
	Given that the user with initials "abcd" is a developer of project "xyz"
   When they create an activity named "research" under the current project
   Then the error message "Developers cannot create new activities" is shown