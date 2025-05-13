Feature: Assigning rooms to schedules

  Background:
    Given I am logged in as "elkes" with password "elkes123"

  Scenario: Assign a room to a schedule - unhappy
    When I navigate to the schedules page
    And I click on schedule with name "Software Engineering"
    Then I should see available rooms
    When I click the book button for room with name "Room 101" in building "Main Building"
    Then I see a schedule assign room error

  Scenario: Assign a room to a schedule - happy path
    When I navigate to the schedules page
    And I click on schedule with name "Software Engineering"
    Then I should see available rooms
    When I click the book button for room with name "Room 202" in building "Technology Center"
    Then no book buttons should be visible
    And schedule with course name "Software Engineering" should have room with name "Room 202"

        
    
    
