Feature: Enrolling students in a schedule

  Background:
    Given I am logged in as "admin" with password "admin123"

  Scenario: Admin enrolls a student in a schedule
    When I navigate to the schedules page
    And I click on schedule with name "Johan Pieck"
    Then I see a list of students who can be enrolled
    When I click the enroll button for student with number "r0785024"
    And the enroll button for student with number "r0785024" should not be visible anymore
    Then the number of enrolled students for schedule with name "Johan Pieck" should be 2


    
