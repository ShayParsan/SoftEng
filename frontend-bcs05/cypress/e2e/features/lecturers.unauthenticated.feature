Feature: Lecturers Overview - Unauthenticated

Scenario: Unauthenticated user attempts to access the lecturers overview page
    Given I am an unauthenticated user
    When I navigate to the lecturers page
    Then I should see an unauthorized error message