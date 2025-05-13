Feature: Buildings Overview - Authenticated

Background:
    Given I am logged in as "elkes" with password "elkes123"

Scenario:  Lecturer attempts to access the buildings overview page
    When I navigate to the buildings page
    Then I should see the buildings overview with rooms and schedules
