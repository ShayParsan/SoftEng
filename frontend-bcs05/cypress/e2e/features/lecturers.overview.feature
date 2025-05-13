Feature: Lecturers Overview - Authenticated

Background:
    Given I am logged in as "johanp" with password "johanp123"

Scenario: Authenticated user attempts to access the lecturers overview page
    When I navigate to the lecturers page
    Then I should see a table with all lecturers

Scenario: Authenticated user sees all lecturers correctly
    When I navigate to the lecturers page
    Then I should see the following lecturers:
        | First Name | Last Name | Email                         | Expertise                           |
        | Johan      | Pieck     | johan.pieck@ucll.be           | Full-stack development, Front-end development |
        | Elke       | Steegmans | elke.steegmans@ucll.be        | Software Engineering, Back-End Development   |
        | Greetje    | Jongen    | greetje.jongen@ucll.be        | Full-Stack development, Back-end Development |
