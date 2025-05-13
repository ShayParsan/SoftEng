Feature: Create Schedule

Background:
    Given I am logged in as "admin" with password "admin123"

Scenario: Admin attempts to create schedule for lecturer with endDate before startDate
    When I navigate to the lecturers page
    And I click on lecturer with name "Johan"
    Then I see a list of courses i can create schedule for
    When I click the Schedule button for course with name "Front-End Development"
    Then I see create schedule form page
    When I fill in schedule from "tomorrow 11:00" to "tomorrow 9:00"
    And I click the button "Create Schedule"
    Then I see error schedule message

Scenario: Admin attempts to create schedule for lecturer
    When I navigate to the lecturers page
    And I click on lecturer with name "Johan"
    Then I see a list of courses i can create schedule for
    When I click the Schedule button for course with name "Front-End Development"
    Then I see create schedule form page
    When I fill in schedule from "tomorrow 09:00" to "tomorrow 11:00"
    And I click the button "Create Schedule"
    Then I see message schedule created successfully
    When I navigate to the schedules page
    Then I should see the following schedules:
      | Course                | Start            | End              | Lecturer         | Enrolled Students |
      | Full-stack development | today 08:30      | today 10:30      | Johan Pieck      | 1                 |
      | Full-stack development | today 13:30      | today 15:30      | Elke Steegmans   | 1                 |
      | Software Engineering   | today 13:30      | today 15:30      | Elke Steegmans   | 1                 |
      | Back-end Development   | today 10:45      | today 12:45      | Greetje Jongen   | 1                 |
      | Front-End Development  | tomorrow 09:00   | tomorrow 11:00   | Johan Pieck      | 0                 |

