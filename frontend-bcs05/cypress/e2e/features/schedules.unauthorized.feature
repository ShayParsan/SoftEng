Feature: Schedules Overview - Unauthenticated

Scenario: Unauthenticated user attempts to access the schedules overview page
    Given I am an unauthenticated user
    When I navigate to the schedules page
    Then I should see an unauthorized error message