Feature: Buildings Overview - Unauthenticated

Scenario: Unauthenticated user attempts to access the buildings overview page
    Given I am an unauthenticated user
    When I navigate to the buildings page
    Then I should see an unauthorized error message